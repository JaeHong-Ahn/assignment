version settings
- Java JDK 1.8, SDK 8
- Spring Boot 2.7.18
- MySql 8.0.36

API 명세

**1. 회원 가입**
/api/user/join, PostMapping

REQUEST
- UserRegisterForm -> String identifier, String password,
String nickname, String name, String phoneNum, Stringemail

- BindingResult

RESPONSE
- 회원 가입 성공 시 201 OK, 실패시 400 에러,

**2. 로그인**
/api/user/login, PostMapping

REQEUST
- UserLoginForm -> String identifier, String password
- BindingResult
- HttpServletRequest

RESPONSE
성공시 200 OK
아이디, 비밀번호 불일치 -> 400 ERROR

**3. 로그 아웃**
/api/user/logout, PostMapping

REQUEST
- HttpServletResponse

RESPONSE
성공 시 200OK

**4. 회원 목록 조회**
/api/user/list, GetMapping

REQUEST
- Long page("pg"), Long pageSize("ps"), SortOption sortOption <- RequestParam
- {"pg": 0, "ps": 10, "option": "LATEST_JOIN"} <- 디폴트

RESPONSE
조건에 맞는 형식의 유저 리스트 페이지

**5. 회원 정보 수정**
/api/user/{identifier}, PostMapping

REQUEST
- UserUpdateForm -> String identifier, String nickname, String phoneNum,
String name, String email
- identifer -> @PathVariable
- HttpServletRequest
- BindingResult

RESPONSE
- UserUpdateResponseDto -> String identifier, String nickname,
String phoneNum, String name, String email

**6. 회원 비밀번호 수정**
api/user/password/{id}, PostMapping

REQUEST
- UserUpdatePasswordForm -> String password, String checkPassword
- id -> @PathVariable
- BindingResult

RESPONSE
성공 시 200 OK with No Data


**7. 회원 탈퇴**
api/user/withdrawal, PostMapping

REQUEST
- HttpServletRequest

RESPONSE
성공 시 200 OK with No Data


구현 해야할 메인 기능은 총 3가지로, 회원 가입, 회원 목록 조회와 회원 정보 수정 기능이었습니다.

회원 가입 기능이 있으면 로그인 기능 로그 아웃기능, 회원 탈퇴 기능이 필요하다고 생각하여 추가하게 되었고,

회원 정보를 수정하는 부분에서 비밀번호 부분은 BCryptPasswordEncoder를 사용하여 구현하였습니다.
