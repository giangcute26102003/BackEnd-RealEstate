package com.example.datn_realeaste_crm.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.CHUA_DOC;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public enum NotificationStatus {
        CHUA_DOC("Chưa đọc"),
        DA_DOC("Đã đọc");

        private final String value;

        NotificationStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static NotificationStatus fromValue(String value) {
            for (NotificationStatus status : values()) {
                if (status.getValue().equals(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("No enum constant with value: " + value);
        }
    }
}