# springMVC
_스프링 웹 개발 방법에는 크게 **세가지**가 있다._
>
**1.[정적 컨텐츠]** : 서버에서 하는 일 없이 웹브라우저에 그대로 내려준다.
**2.[MVC 와 템플릿 엔진]** : 과거의  JSP PHP 처럼 HTML 을 동적으로 바꿔서 내려준다.
**3.[API]**: 안드로이드나 아이폰 클라이언트와 개발해야 할 때 JSON 이라는 데이터 포맷으로 클라이언트에게 전달 /**서버-서버간 통신 ,데이터를 내려줄 때에 사용된다.**

## [정적 컨텐츠]

_/resources/static 폴더 안에 존재 _

```jsx
<!DOCTYPE HTML>
<html>
<head>
 <title>Hello</title>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
Hello
<a href="/hello">hello</a>
</body>
</html>
```
> 웹브라우저의 요청을 내장 톰캣서버가 받으면 스프링에게 넘간다. 
우선순위를 가진 컨트롤러에 관련 컨트롤러가 있는지 확인하고, 없으면 resources 에서 html을 그대로 반환한다.
![](https://images.velog.io/images/jinii/post/3290b171-32ca-407a-97a0-c63eef5cf997/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%209.25.23.png)




## [MVC와 템플릿 엔진]

_<관심사 분리!> 뷰는 화면을 그리는데 집중, 컨트롤러는 로직 처리에 집중 
MVC : Model, View, Controller_

**Controller**

```jsx
@Controller
public class HelloController {
 @GetMapping("hello")
 public String hello(Model model) {
   model.addAttribute("data", "hello!!");
   return "hello";
 }

@GetMapping("hello-mvc")
 public String helloMvc(@RequestParam("name")String name, Model model) {
   model.addAttribute("name", name); //key , value
   return "hello-template"; 
 }
}
```

**View**

```jsx
<html xmlns:th="http://www.thymeleaf.org">
<body>
<p th:text="'hello ' + ${name}">hello! empty</p>
</body>
```
> 웹브라우저에서 [localhost:8080/hello-mvc](http://localhost:8080/hello-mvc) 넘긴 후 내장 톰캣 서버에 스프링 컨테이너 안의 helloController가 있는지 확인 한다.            
있으면 , hello-template에 model 로  키 밸류 형태의 name: spring을 넘겨준다 . 
그리고 viewResolver가 뷰를 찾아서 템플릿 엔진을 처리한 후 HTML 을 변환 시켜 웹 브라우저에 전달해준다.
![](https://images.velog.io/images/jinii/post/10298e7e-47c5-4f32-bf54-e3b77f7a553e/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%209.35.20.png)

 

## **[API]**

**@ResponseBody 문자 반환** 

```jsx
@Controller
public class HelloController {

 @GetMapping("hello-string")
 @ResponseBody

 public String helloString(@RequestParam("name") String name) {
   return "hello " + name; //hello spring
 }

@GetMapping("hello-api")
@ResponseBody
 public Hello helloApi(@RequestParam("name") String name) {
   Hello hello = new Hello();
   hello.setName(name);
   return hello;
 }
 static class Hello {
   private String name;
   public String getName() {
     return name;
	 }
 public void setName(String name) {
   this.name = name;
    }
  }
}
```

> HTTP의 BODY에 문자열을 직접 반환 .
> html 관련이 아니라 그냥 그대로 데이터를 내려준다.
> 객체로 만들어 JSON 데이터를 내려준다.


>  웹브라우저가 [localhost:8080/hello-api를](http://localhost:8080/hello-api를) 보내면 스프링 부트 안에 있는 내장 톰캣 서버가 helloController안에 hello-api 가 있는 것을 발견한다.                                                   
이 때 @ResponseBody 이고 객체 타입으로 반환된 hello를 HttpMessageConverter에 Json 이나 String 형식으로 변환한다. 
( 기본은 Json).                                            
그리고 변환된 타입을 웹브라우저에 내린다 .
![](https://images.velog.io/images/jinii/post/11686ed4-4645-4b06-a3f8-9e92fe98f899/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%209.46.16.png)



#### 비즈니스 요구사항 정리
#### 회원 도메인과 레포지토리 만들기
#### 회원 레포지토리 테스트 케이스 작성
#### 회원 서비스 개발
#### 회원 서비스 테스트

## 비즈니스 요구사항 정리

_데이터  : 회원 ID , 이름
기능: 회원 등록, 조회
아직 데이터 저장소가 선정 되지 않음 (가상의 시나리오)_

![](https://images.velog.io/images/jinii/post/e6f09b0d-e036-4192-ad2a-2bc6e06a5c27/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%2010.14.33.png)

- 컨트롤러 : 웹 MVC 의 컨트롤러 역할
- 서비스 : 핵심 비즈니스 로직 구현 ( 회원은 중복 가입이 안된다 )
- 리포지토리 : 데이터 베이스에 접근, 도메인 객체를 DB에 저장 하고 관리
- 도메인 : 비즈니스 도메인 객체, 예) 회원, 주문, 쿠폰 등 주로 데이터 베이스에 저장 하고 관리됨

![](https://images.velog.io/images/jinii/post/613af8cc-6ffa-4561-9404-1395edf1ea69/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%2010.17.32.png)

- 아직 데이터 저장소가 선정 되지 않아서, 우선 인터페이스로 구현 클래스를 변경 할 수 있도록 설계
- 데이터 저장소는 RDB, NoSQL 등 다양한 저장소를 고민중인 상황으로 가정
- 개발을 진행하기 위해서 초기 계발 단계 에서는 구현체로 가벼운 메모리 기반의 데이터 저장소 사용

## 회원 도메인과 레포지토리 만들기

**회원 객체**

```jsx
package hello.hellospring.domain;
public class Member {

 private Long id;
 private String name;
  
 public Long getId() {
 return id;
 }
  
 public void setId(Long id) {
 this.id = id;
 }
  
 public String getName() {
 return name;
 }
  
 public void setName(String name) {
 this.name = name;
   
 }
  
}
```

**회원 레포지토리 인터페이스** 

```jsx
package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
  
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
  
}
```

**회원 리포지토리 메모리 구현체**

```jsx
package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;
/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
@Repository
public class MemoryMemberRepository implements MemberRepository {
  
 private static Map<Long, Member> store = new HashMap<>();
 private static long sequence = 0L;

 @Override
 public Member save(Member member) {
   member.setId(++sequence);
   store.put(member.getId(), member);
   return member;
 }

 @Override
 public Optional<Member> findById(Long id) {
   return Optional.ofNullable(store.get(id));
 }

 @Override
 public List<Member> findAll() {
   return new ArrayList<>(store.values());
 }

 @Override
 public Optional<Member> findByName(String name) {
   return store.values().stream()
     .filter(member -> member.getName().equals(name))
     .findAny();
 }

 public void clearStore() {
   store.clear();
 }
}
```

**회원 레포지토리 테스트 케이스 작성**

```jsx
package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

  MemoryMemberRepository repository = new MemoryMemberRepository();

  @AfterEach
  public void afterEach() {
    repository.clearStore();
  }

  @Test
  public void save() {

    //given

    Member member = new Member();
    member.setName("spring");

    //when

    repository.save(member);

    //then

    Member result = repository.findById(member.getId()).get();
    assertThat(result).isEqualTo(member);
  }

@Test
public void findByName() {

  //given

  Member member1 = new Member();
  member1.setName("spring1");
  repository.save(member1);
  
  Member member2 = new Member();
  member2.setName("spring2");
  repository.save(member2);

  //when

  Member result = repository.findByName("spring1").get();
  assertThat(result).isEqualTo(member1);

}

@Test
public void findAll() {

  //given

  Member member1 = new Member();
  member1.setName("spring1");
  repository.save(member1);
  
  Member member2 = new Member();
  member2.setName("spring2");
  repository.save(member2);

  //when

  List<Member> result = repository.findAll();

  //then

  assertThat(result.size()).isEqualTo(2);
	}
}
```

**회원 서비스 개발**

```jsx
package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;
@Service
public class MemberService {

  private final MemberRepository memberRepository ;

  @Autowired
  public MemberService(MemberRepository memberRepository){
    this.memberRepository = memberRepository;
  
 /**
 * 회원가입
 */

 public Long join(Member member) {

   validateDuplicateMember(member); //중복 회원 검증
   memberRepository.save(member);
   return member.getId();
 }

 private void validateDuplicateMember(Member member) {

   memberRepository.findByName(member.getName())
     .ifPresent(m -> {
     	throw new IllegalStateException("이미 존재하는 회원입니다.");
 	});
 }

 /**
 * 전체 회원 조회
 */

 public List<Member> findMembers() {
   return memberRepository.findAll();
 }

 public Optional<Member> findOne(Long memberId) {
   return memberRepository.findById(memberId);
 	}
}
```

**회원 서비스 테스트** 

```jsx
package hello.hellospring.service;
import hello.hellospring.domain.Member;

import hello.hellospring.repository.MemoryMemberRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
  MemberService memberService;
  MemoryMemberRepository memberRepository;

  @BeforeEach // DI
  public void beforeEach() {
    memberRepository = new MemoryMemberRepository();
    memberService = new MemberService(memberRepository);
  }

  @AfterEach
  public void afterEach() {
    memberRepository.clearStore();
  }

  @Test
  public void 회원가입() throws Exception {
    //Given
    Member member = new Member();
    member.setName("hello");

    //When
    Long saveId = memberService.join(member);

    //Then
    Member findMember = memberRepository.findById(saveId).get();
    assertEquals(member.getName(), findMember.getName());
  }

@Test
public void 중복_회원_예외() throws Exception {
  //Given
  Member member1 = new Member();
  member1.setName("spring");
  
  Member member2 = new Member();
  member2.setName("spring");

  //When
  memberService.join(member1);
  IllegalStateException e = assertThrows(IllegalStateException.class,
      () -> memberService.join(member2));//예외가 발생해야 한다.
  assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

	}
}
```
![](https://images.velog.io/images/jinii/post/72943da5-1e7f-4e27-bee3-d392f7fefa80/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%2011.33.50.png)

> DI 는 @annotation 과 Autowired 를 통해 이루어짐
> 

**스프링 빈을 등록하는 두가지 방법**

- 컴포넌트 스캔과 자동 의존 관계 설정
- 자바 코드로 직접 스프링 빈 등록

## 컴포넌트 스캔과 자동 의존 관계 설정

**@Service, @Controller, @Repository  or @Component**

패키지 포함 하위 클래스들은  자동으로 컴포넌트 스캔의 대상임 

**기본으로 싱글톤으로 등록한다.(유일하게 하나만 등록해서 공유함)**

```jsx
package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

   private final MemberService memberService;

   @Autowired
   public MemberController(MemberService memberService) {
          this.memberService = memberService;
   }
}
```

**회원 서비스 빈 등록**

```jsx
@Service
public class MemberService {
  private final MemberRepository memberRepository;

  @Autowired
  public MemberService(MemberRepository memberRepository) {
    	this.memberRepository = memberRepository;
 }
}
```

## 자바 코드로 직접 스프링 빈 등록하기

_장점 : 데이터 저장소가 선정되지 않아서, 인터페이스로 구현 클래스를 변경할 수 있도록 설계 할 경우에 레포지토리만 바꾸면 돼서 편하다.
_
*SpringConfig* 

```jsx
package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository());
	 }

  @Bean
  public MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }
}
```

> 참고: DI에는 필드 주입, setter 주입, 생성자 주입 이렇게 3가지 방법이 있다. 의존관계가 실행중에
동적으로 변하는 경우는 거의 없으므로 생성자 주입을 권장한다.
> 

> 주의: @Autowired 를 통한 DI는 helloConroller , memberService 등과 같이 스프링이 관리하는객체에서만 동작한다. 
스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않는다.
>


- [회원 웹 기능 - 홈 화면 추가 ]
- [회원 웹 기능 - 등록]
- [회원 웹 기능 - 조회]

## 회원 웹 기능 - 홈 화면 추가

**홈 컨트롤러 추가**

```jsx
package hello.hellospring.controller;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/")
  public String home() {
    return "home";
  }
}
```

**회원 관리용 홈**

```jsx
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<body>
  <div class="container">
    <div>
      <h1>Hello Spring</h1>
      <p>회원 기능</p>
      <p>
        <a href="/members/new">회원 가입</a>
        <a href="/members">회원 목록</a>
      </p>
    </div>
  </div> <!-- /container -->
</body>
</html>
```

## 회원 웹 기능 - 등록

**회원 등록 폼 컨트롤러**

```jsx
@Controller
public class MemberController {
  private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
      this.memberService = memberService;
    }
  
    @GetMapping(value = "/members/new")
    public String createForm() {
      return "members/createMemberForm";
   }
}
```

**회원 등록 폼 HTML**

```jsx
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<body>
  <div class="container">
    <form action="/members/new" method="post">
      <div class="form-group">
        <label for="name">이름</label>
        <input type="text" id="name" name="name" placeholder="이름을
                                                              입력하세요">
          </div>
        <button type="submit">등록</button>
        </form>
      </div> <!-- /container -->
</body>
</html>
```

**웹 등록 화면에서 데이터를 전달 받을 폼 객체**

```jsx
package hello.hellospring.controller;

public class MemberForm {
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
```

**회원 컨트롤러에서 회원을 실제 등록하는 기능**

```jsx
@PostMapping(value = "/members/new")
public String create(MemberForm form) {

  Member member = new Member();

  member.setName(form.getName());
  memberService.join(member);

  return "redirect:/";
}
```

## 회원 웹 기능 - 조회

**회원 컨트롤러에서 조회 기능**

```jsx
@GetMapping(value = "/members")
public String list(Model model) {
  List<Member> members = memberService.findMembers();
  model.addAttribute("members", members);
  return "members/memberList";
}
```

**회원 리스트 HTML**

```jsx
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<body>
  <div class="container">
    <div>
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>이름</th>
          </tr>
        </thead>
        <tbody>
          <tr th:each="member : ${members}">
            <td th:text="${member.id}"></td>
            <td th:text="${member.name}"></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div> <!-- /container -->
</body>
</html>
```


스프링 데이터 엑세스 

- [H2 데이터 베이스 설치]
- [순수 Jdbc]
- [스프링 JdbcTemplate]
- [JPA]
- [스프링 데이터 JPA]

## H2 데이터베이스

**테이블 생성하기**

*테이블 관리를 위해 프로젝트 루트에 sql/ddl.sql 파일을 생성*

```jsx
drop table if exists member CASCADE;
create table member
(
 id bigint generated by default as identity,
 name varchar(255),
 primary key (id)
);
```

**스프링 통합 테스트**

```jsx
package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional //beforeEach 없어도 됨 
class MemberServiceIntegrationTest {
  @Autowired MemberService memberService;
  @Autowired MemberRepository memberRepository;
  @Test
  public void 회원가입() throws Exception {
    //Given
       Member member = new Member();
       member.setName("hello");

       //When
       Long saveId = memberService.join(member);

       //Then
       Member findMember = memberRepository.findById(saveId).get();
       assertEquals(member.getName(), findMember.getName());
     }
@Test
public void 중복_회원_예외() throws Exception {
  //Given
  Member member1 = new Member();
  member1.setName("spring");

  Member member2 = new Member();
  member2.setName("spring");

  //When
  memberService.join(member1);
  IllegalStateException e = assertThrows(IllegalStateException.class,
                                         () -> memberService.join(member2));//예외가 발생해야 한다.
  assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
}
}
```

> **@SpringBootTest** : 스프링 컨테이너와 테스트를 함께 실행한다.
**@Transactional** : 테스트 케이스에 이 애노테이션이 있으면, 
테스트 시작 전에 트랜잭션을 시작하고,테스트 완료 후에 항상 롤백한다. 
이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지
않는다
> 

## JPA

객체와 ORM 매핑 

엔티티와 PK

> JPA는 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.
JPA를 사용하면, **SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환**을 할 수 있다.
JPA를 사용하면 개발 생산성을 크게 높일 수 있다.
> 

**JPA 엔티티 매핑**

```jsx
package hello.hellospring.domain;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
@Entity
public class Member {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
```

**JPA 회원 리포지토리**

```jsx
package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
  private final EntityManager em;
  public JpaMemberRepository(EntityManager em) {
    this.em = em;
  }
  public Member save(Member member) {
    em.persist(member);
    return member;
 }

 public Optional<Member> findById(Long id) {
   Member member = em.find(Member.class, id);
   return Optional.ofNullable(member);
 }

 public List<Member> findAll() {
   return em.createQuery("select m from Member m", Member.class)
     .getResultList();
 }
 public Optional<Member> findByName(String name) {
 //JPQL 테이블 대상이 아니라 객체 대상으로 쿼리를 날림 -> PK 기반이 아니면 작성해야함
   List<Member> result = em.createQuery("select m from Member m where
                                        m.name = :name", Member.class)
                                        .setParameter("name", name)
     .getResultList();
   return result.stream().findAny();
 }
}
```

**서비스 계층에 트랜잭션 추가**

```jsx
import org.springframework.transaction.annotation.Transactional
@Transactional
public class MemberService {}
```

**JPA를 사용하도록 스프링 설정 변경**

```jsx
package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.persistence.EntityManager;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
  private final DataSource dataSource;
  private final EntityManager em;
  public SpringConfig(DataSource dataSource, EntityManager em) {
    this.dataSource = dataSource;
    this.em = em;
 }
 @Bean
 public MemberService memberService() {
   return new MemberService(memberRepository());
 }
 @Bean
 public MemberRepository memberRepository() {
// return new MemoryMemberRepository();
// return new JdbcMemberRepository(dataSource);
// return new JdbcTemplateMemberRepository(dataSource);
   return new JpaMemberRepository(em);
 }
}
```

**스프링 데이터 JPA**

**스프링 데이터 JPA 회원 리포지토리**

```jsx
package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member,
Long>, MemberRepository {
 	Optional<Member> findByName(String name);
}
```

**스프링 데이터 JPA 회원 리포지토리를 사용하도록 스프링 설정 변경**

```jsx
package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
  private final MemberRepository memberRepository;
  public SpringConfig(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
 }

 @Bean
 public MemberService memberService() {
   return new MemberService(memberRepository);
 }
}
```

> 스프링 데이터 JPA가 SpringDataJpaMemberRepository 를 스프링 빈으로 자동 등록해준다.
> 

**스프링 데이터 JPA 제공 클래스**

![](https://images.velog.io/images/jinii/post/f987799f-a6f5-4dda-8eb4-ffb7c938e794/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%202.37.38.png)

**스프링 데이터 JPA 제공 기능**

> 인터페이스를 통한 기본적인 CRUD
findByName() , findByEmail() 처럼 메서드 이름 만으로 조회 기능 제공
페이징 기능 자동 제공
> 

> 참고: 실무에서는 JPA와 스프링 데이터 JPA를 기본으로 사용하고, 복잡한 동적 쿼리는 Querydsl이라는
라이브러리를 사용하면 된다**. Querydsl을** 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적
쿼리도 편리하게 작성할 수 있다. 이 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를
사용하거나, 앞서 학습한 스프링 **JdbcTemplate**를 사용하면 된다.
>


## AOP가 필요한 상황

- 모든 메소드의 호출 시간을 측정하고 싶다면?
- 공통 관심 사항 vs 핵심 관심 사항
- 회원 가입 시간, 회원 조회 시간

![](https://images.velog.io/images/jinii/post/61eaa91a-decf-426a-836c-666e303a5e6d/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%202.50.10.png)

> 
> 
> 
> **문제**
> 회원가입, 회원 조회에 시간을 측정하는 기능은 **핵심 관심 사항이 아니다.**
> 
> **시간을 측정하는 로직은 공통 관심 사항**이다.
> 
> 시간을 측정하는 로직과 핵심 비즈니스의 로직이 **섞여서 유지보수가 어렵다.**
> 
> **시간을 측정하는 로직을 별도의 공통 로직으로 만들기 매우 어렵다.**
> 
> **시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다.**
> 

## AOP 적용

> AOP: Aspect Oriented Programming
**공통 관심 사항**(cross-cutting concern) vs **핵심 관심 사항**(core concern) 분리
> 

![](https://images.velog.io/images/jinii/post/6a40e88b-03cf-41c2-9fb8-0562b926f2db/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%202.56.51.png)

**시간 측정 AOP 등록**

```jsx
package hello.hellospring.aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeTraceAop {
  
 @Around("execution(* hello.hellospring..*(..))")
 public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
   long start = System.currentTimeMillis();
   System.out.println("START: " + joinPoint.toString());
   
   try {
     	return joinPoint.proceed();
   } finally {
       long finish = System.currentTimeMillis();
       long timeMs = finish - start;
       System.out.println("END: " + joinPoint.toString()+ " " + timeMs +
                          "ms");
    }
  }
}
```

> **해결**
회원가입, 회원 조회등 핵심 관심사항과 시간을 측정하는 공통 관심 사항을 분리한다.
시간을 측정하는 로직을 별도의 공통 로직으로 만들었다.
핵심 관심 사항을 깔끔하게 유지할 수 있다.
변경이 필요하면 이 로직만 변경하면 된다.
원하는 적용 대상을 선택할 수 있다.
> 

### **스프링의 AOP 동작 방식 설명**

**AOP 적용 전 의존관계**

![](https://images.velog.io/images/jinii/post/8a8d92d0-5718-4703-b901-2f4cb5a0f2eb/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%203.04.16.png)

**AOP 적용 후 의존관계**

![](https://images.velog.io/images/jinii/post/5746b1a1-f42e-420f-b4bd-781c02449006/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%203.05.30.png)
_컨트롤러가 서비스를 주입 할 때 프록시라는 가짜 서비스를 호출하고 joinPoint.proceed() 가 끝나면 실제 서비스를 호출한다._

**AOP 적용 전 전체 그림**

![](https://images.velog.io/images/jinii/post/fa9a8960-ebf9-4a4d-b48f-8d52dffc4245/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%203.07.18.png)

**AOP 적용 후 전체 그림** 

![](https://images.velog.io/images/jinii/post/75ed4a53-ed09-444a-9259-30f3240c1537/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-08%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%203.07.40.png)
