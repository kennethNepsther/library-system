package it.nepsther.library_system.service;

import it.nepsther.library_system.entity.MemberEntity;

import java.util.List;

public interface MemberService {
    MemberEntity findById(Long id);
    MemberEntity saveMember(MemberEntity member);
    void deleteMemberById(Long id);
    List<MemberEntity> findAllMembers();
}
