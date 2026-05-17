package service

import (
	"testing"

	"github.com/maiko26/multi-backend-app/backend/golang/internal/repository"
	"github.com/maiko26/multi-backend-app/backend/golang/internal/testutil"
)

func setupTaskTestDB(t *testing.T) (*repository.TaskRepository, *TaskService) {
	t.Helper()
	db := testutil.SetupTestDB(t)
	repo := repository.NewTaskRepository(db)
	svc := NewTaskService(repo, db)
	return repo, svc
}

func TestTaskService_GetAllTasks(t *testing.T) {
	_, svc := setupTaskTestDB(t)

	tasks, err := svc.GetAllTasks()
	if err != nil {
		t.Fatalf("GetAllTasks() error = %v", err)
	}

	if len(tasks) != 0 {
		t.Errorf("Expected 0 tasks, got %d", len(tasks))
	}
}

func TestTaskService_CreateTask(t *testing.T) {
	_, svc := setupTaskTestDB(t)

	task, err := svc.CreateTask("Test Task", "Test Description", "project-1")
	if err != nil {
		t.Fatalf("CreateTask() error = %v", err)
	}

	if task.ID == "" {
		t.Fatal("CreateTask() did not set task ID")
	}

	if task.Name != "Test Task" {
		t.Errorf("Expected name 'Test Task', got '%s'", task.Name)
	}

	if task.Description != "Test Description" {
		t.Errorf("Expected description 'Test Description', got '%s'", task.Description)
	}

	if task.ProjectID != "project-1" {
		t.Errorf("Expected projectID 'project-1', got '%s'", task.ProjectID)
	}

	if task.Finished != false {
		t.Errorf("Expected finished to be false, got true")
	}
}

func TestTaskService_GetTasksByProjectID(t *testing.T) {
	_, svc := setupTaskTestDB(t)

	svc.CreateTask("Task 1", "Desc 1", "proj-1")
	svc.CreateTask("Task 2", "Desc 2", "proj-1")
	svc.CreateTask("Task 3", "Desc 3", "proj-2")

	tasks, err := svc.GetTasksByProjectID("proj-1")
	if err != nil {
		t.Fatalf("GetTasksByProjectID() error = %v", err)
	}

	if len(tasks) != 2 {
		t.Errorf("Expected 2 tasks for proj-1, got %d", len(tasks))
	}
}

func TestTaskService_UpdateTask(t *testing.T) {
	_, svc := setupTaskTestDB(t)

	task, _ := svc.CreateTask("Original", "Original Desc", "proj-1")

	updated, err := svc.UpdateTask(task.ID, "Updated Name", "Updated Desc", true, "user@test.com")
	if err != nil {
		t.Fatalf("UpdateTask() error = %v", err)
	}

	if updated.Name != "Updated Name" {
		t.Errorf("Expected name 'Updated Name', got '%s'", updated.Name)
	}

	if updated.Description != "Updated Desc" {
		t.Errorf("Expected description 'Updated Desc', got '%s'", updated.Description)
	}

	if updated.Finished != true {
		t.Errorf("Expected finished to be true, got false")
	}

	if updated.FinishedBy != "user@test.com" {
		t.Errorf("Expected finishedBy 'user@test.com', got '%s'", updated.FinishedBy)
	}
}

func TestTaskService_UpdateTaskPartial(t *testing.T) {
	_, svc := setupTaskTestDB(t)

	task, _ := svc.CreateTask("Original", "Original Desc", "proj-1")

	updated, err := svc.UpdateTask(task.ID, "", "", false, "")
	if err != nil {
		t.Fatalf("UpdateTask() error = %v", err)
	}

	if updated.Name != "Original" {
		t.Errorf("Expected name to remain 'Original', got '%s'", updated.Name)
	}

	if updated.Description != "Original Desc" {
		t.Errorf("Expected description to remain 'Original Desc', got '%s'", updated.Description)
	}
}

func TestTaskService_DeleteTask(t *testing.T) {
	_, svc := setupTaskTestDB(t)

	task, _ := svc.CreateTask("To Delete", "Desc", "proj-1")

	err := svc.DeleteTask(task.ID)
	if err != nil {
		t.Fatalf("DeleteTask() error = %v", err)
	}

	tasks, _ := svc.GetAllTasks()
	if len(tasks) != 0 {
		t.Errorf("Expected 0 tasks after delete, got %d", len(tasks))
	}
}

func TestTaskService_DeleteTaskNotFound(t *testing.T) {
	_, svc := setupTaskTestDB(t)

	err := svc.DeleteTask("non-existent")
	if err != nil {
		t.Fatalf("DeleteTask() should not error for non-existent task, got %v", err)
	}
}

func TestTaskService_AddComment(t *testing.T) {
	_, svc := setupTaskTestDB(t)

	task, _ := svc.CreateTask("Task With Comment", "Desc", "proj-1")

	comment, err := svc.AddComment(task.ID, "user@test.com", "Great task!")
	if err != nil {
		t.Fatalf("AddComment() error = %v", err)
	}

	if comment.TaskID != task.ID {
		t.Errorf("Expected comment taskID '%s', got '%s'", task.ID, comment.TaskID)
	}

	if comment.UserID != "user@test.com" {
		t.Errorf("Expected comment userID 'user@test.com', got '%s'", comment.UserID)
	}

	if comment.Content != "Great task!" {
		t.Errorf("Expected comment content 'Great task!', got '%s'", comment.Content)
	}
}

func TestTaskService_ToggleStar(t *testing.T) {
	_, svc := setupTaskTestDB(t)

	task, _ := svc.CreateTask("Starred Task", "Desc", "proj-1")

	starred, err := svc.ToggleStar("user@test.com", task.ID)
	if err != nil {
		t.Fatalf("ToggleStar() error = %v", err)
	}

	if !starred {
		t.Errorf("Expected task to be starred, got unstarred")
	}

	unstarred, err := svc.ToggleStar("user@test.com", task.ID)
	if err != nil {
		t.Fatalf("ToggleStar() second call error = %v", err)
	}

	if unstarred {
		t.Errorf("Expected task to be unstarred on second toggle, got starred")
	}
}

func TestTaskService_MultipleUsersStarTask(t *testing.T) {
	_, svc := setupTaskTestDB(t)

	task, _ := svc.CreateTask("Multi Star", "Desc", "proj-1")

	svc.ToggleStar("user1@test.com", task.ID)
	svc.ToggleStar("user2@test.com", task.ID)
	svc.ToggleStar("user3@test.com", task.ID)

	tasks, _ := svc.GetAllTasks()
	if len(tasks) != 1 {
		t.Fatalf("Expected 1 task, got %d", len(tasks))
	}

	if len(tasks[0].StarredBy) != 3 {
		t.Errorf("Expected 3 starredBy entries, got %d", len(tasks[0].StarredBy))
	}
}

func TestTaskService_UpdateTaskNotFound(t *testing.T) {
	_, svc := setupTaskTestDB(t)

	_, err := svc.UpdateTask("non-existent", "New Name", "", false, "")
	if err == nil {
		t.Fatal("UpdateTask() expected error for non-existent task, got nil")
	}
}

func TestTaskService_AddCommentToNonExistentTask(t *testing.T) {
	_, svc := setupTaskTestDB(t)

	comment, err := svc.AddComment("non-existent", "user@test.com", "Comment")
	if err != nil {
		t.Fatalf("AddComment() error = %v", err)
	}

	if comment.TaskID != "non-existent" {
		t.Errorf("Expected comment taskID 'non-existent', got '%s'", comment.TaskID)
	}
}
