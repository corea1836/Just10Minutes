package flab.just10minutes.member.repository;

import flab.just10minutes.member.Entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface MemberDao {

    int save(Member member);
    Member findById(String id);
    String findId(String id);
    Member findByMemberUniqueId(Long uniqueId);
    int updateMemberBalance(Member member);

}
