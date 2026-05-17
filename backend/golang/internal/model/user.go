package model

import "time"

type User struct {
	ID                  uint       `json:"id" gorm:"primaryKey"`
	CreatedAt           time.Time  `json:"createdAt"`
	UpdatedAt           time.Time  `json:"updatedAt"`
	Email               string     `json:"email" gorm:"uniqueIndex;not null"`
	Password            string     `json:"-" gorm:"not null"`
	Name                string     `json:"name" gorm:"not null"`
	AvatarURL           string     `json:"avatar"`
	PhoneNumber         string     `json:"phoneNumber"`
	Role                string     `json:"role" gorm:"default:'user'"`
	IsVerified          bool       `json:"isVerified" gorm:"default:false"`
	VerificationToken   string     `json:"-"`
	ResetPasswordToken  string     `json:"-"`
	ResetPasswordExpiry *time.Time `json:"-"`
	FailedLoginAttempts int        `json:"-" gorm:"default:0"`
	AccountLockedUntil  *time.Time `json:"-"`
	LastLogin           *time.Time `json:"lastLogin"`
	SubscriptionStatus  string     `json:"subscriptionStatus" gorm:"default:'free'"`
	SubscriptionID      string     `json:"subscriptionId"`
	CustomerID          string     `json:"customerId"`
	MBAPoints           int        `json:"mbaPoints" gorm:"default:0"`
	EmailNotifications  bool       `json:"emailNotifications" gorm:"default:true"`
	PushNotifications   bool       `json:"pushNotifications" gorm:"default:true"`

	UserSettings   *UserSettings   `gorm:"foreignKey:UserEmail;references:Email" json:"userSettings"`
	TeamMembers    []TeamMember    `gorm:"foreignKey:Email;references:Email" json:"teamMembers"`
	ProjectMembers []ProjectMember `gorm:"foreignKey:Email;references:Email" json:"projectMembers"`
	Tasks          []Task          `gorm:"foreignKey:FinishedBy;references:Email" json:"tasks"`
	Comments       []TaskComment   `gorm:"foreignKey:UserID;references:Email" json:"taskComments"`
	StarredTasks   []StarredTask   `gorm:"foreignKey:UserID;references:Email" json:"starredTasks"`
	Activities     []ActivityHistory `gorm:"foreignKey:UserID;references:Email" json:"activities"`
	Notifications  []UserNotification `gorm:"foreignKey:UserID;references:Email" json:"userNotifications"`
	Sessions       []Session       `gorm:"foreignKey:Email;references:Email" json:"sessions"`
}
