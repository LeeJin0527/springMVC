package com.example.springMVC.Repository;

import com.example.springMVC.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;
//@Repository
public class MemberRepositoryImpl implements  MemberRepository{

    //Java에서Map은 키-값 쌍으로 데이터를 저장하는 데 사용되는 인터페이스 인 반면 HashMap은 Map 인터페이스의 구현 클래스입니다.
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public  Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }
    @Override
    public Optional<Member> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }
    @Override
    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }
    @Override
    public Optional<Member> findByName(String name){
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    public void clearStore(){
        store.clear();
    }
}
