package utils

import (
	"fmt"
	"strconv"

	"gopkg.in/gomail.v2"
)

type EmailSender struct {
	host     string
	port     int
	user     string
	password string
	from     string
}

func NewEmailSender(host string, port string, user, password, from string) *EmailSender {
	p, _ := strconv.Atoi(port)
	return &EmailSender{
		host:     host,
		port:     p,
		user:     user,
		password: password,
		from:     from,
	}
}

func (s *EmailSender) SendVerificationEmail(to, token string) error {
	link := fmt.Sprintf("http://localhost:5173/auth/email-confirmation/%s", token)
	subject := "Verify your email"
	body := fmt.Sprintf(`<div>
		<div>Your verification link is:</div>
		<a href="%s">%s</a>
	</div>`, link, link)
	return s.send(to, subject, body)
}

func (s *EmailSender) SendPasswordResetEmail(to, token string) error {
	link := fmt.Sprintf("http://localhost:5173/auth/password-reset/%s", token)
	subject := "Password Reset Code"
	body := fmt.Sprintf(`<p>Your password reset code is: <strong><a href="%s">%s</a></strong></p>`, link, link)
	return s.send(to, subject, body)
}

func (s *EmailSender) send(to, subject, body string) error {
	m := gomail.NewMessage()
	m.SetHeader("From", s.from)
	m.SetHeader("To", to)
	m.SetHeader("Subject", subject)
	m.SetBody("text/html", body)

	d := gomail.NewDialer(s.host, s.port, s.user, s.password)
	return d.DialAndSend(m)
}
