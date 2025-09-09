package it.nepsther.library_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "memberId")
@Table(name = "tb_member", uniqueConstraints = {
        @UniqueConstraint(name = "UK_tb_member_email", columnNames = "email"),
        @UniqueConstraint(name = "UK_tb_member_telephone", columnNames = "telephone")
})
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "full_name")
    private String fullName;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "telephone", nullable = false, unique = true)
    private String telephone;
    @Column(name = "join_date", nullable = false)
    private LocalDate joinDate;
}
