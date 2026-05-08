package controllers

import (
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/maiko26/multi-backend-app/backend/golang/models"
)

// GetUsers gets all existing users.
func GetUsers(c *gin.Context) {
	var users []models.User

	err := models.GetUsers(&users)

	if err != nil {
		c.AbortWithStatus(http.StatusBadRequest)
	} else {
		c.JSON(http.StatusOK, users)
	}
}

// GetUser finds a single user by ID.
func GetUser(c *gin.Context) {
	var user models.User

	err := models.GetUser(&user, c.Param("id"))

	fmt.Println("hello world")

	if err != nil {
		c.AbortWithStatus(http.StatusNotFound)
	} else {
		c.JSON(http.StatusOK, user)
	}
}

// CreateUser creates a new user.
func CreateUser(c *gin.Context) {
	var user models.User

	c.BindJSON(&user)

	err := models.CreateUser(&user)

	if err != nil {
		c.AbortWithStatus(http.StatusBadRequest)
	} else {
		c.JSON(http.StatusCreated, user)
	}
}

// UpdateUser updates a new user by ID.
func UpdateUser(c *gin.Context) {
	var user models.User

	err := models.GetUser(&user, c.Param("id"))

	if err != nil {
		c.AbortWithStatus(http.StatusNotFound)
		return
	}

	c.BindJSON(&user)

	err = models.UpdateUser(&user)

	if err != nil {
		c.AbortWithStatus(http.StatusBadRequest)
	} else {
		c.JSON(http.StatusOK, user)
	}
}

// DeleteUser deletes a user by ID.
func DeleteUser(c *gin.Context) {
	var user models.User

	err := models.GetUser(&user, c.Param("id"))

	if err != nil {
		c.AbortWithStatus(http.StatusNotFound)
		return
	}

	err = models.DeleteUser(&user, c.Param("id"))

	if err != nil {
		c.AbortWithStatus(http.StatusBadRequest)
	} else {
		c.Status(http.StatusNoContent)
	}
}
