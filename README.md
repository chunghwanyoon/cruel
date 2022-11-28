# Cruel

## 로컬 개발환경 구성
### JDK 17 설치
#### 1. JDK 다운로드

아래 링크에서 개발 환경에 맞는 JDK를 다운로드합니다.
- [macOS/AArch64 (M1)](https://download.java.net/java/GA/jdk17/0d483333a00540d886896bac774ff48b/35/GPL/openjdk-17_macos-aarch64_bin.tar.gz)
- [macOS/x64 (Intel)](https://download.java.net/java/GA/jdk17/0d483333a00540d886896bac774ff48b/35/GPL/openjdk-17_macos-x64_bin.tar.gz)

Mac OS를 사용하고 있지 않다면, Oracle에서 제공하는 JDK를 설치해도 무방합니다.

#### 2. JDK 설치

다운로드 받은 JDK를 로컬 환경에 설치합니다.
```shell
$ sudo mv openjdk-17_macos-aarch64_bin.tar.gz /Library/Java/JavaVirtualMachines/
$ cd /Library/Java/JavaVirtualMachines
$ sudo tar -xzf openjdk-17_macos-aarch64_bin.tar.gz
$ sudo rm openjdk-17_macos-aarch64_bin.tar.gz
```

설치 후 자바 환경변수를 `~/.bashrc` 또는 `~/.zshrc`에 등록합니다. 경로가 아래 예시와 다를 수 있으니 반드시 설치된 환경에서 확인 후 설치해야 합니다.
```shell
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home
```

#### 3. JDK 설치 확인

새로운 터미널을 열거나 `source ~/.zshrc` 명령어를 호출하여 자바 환경변수 설정을 쉘에 적용한 후 아래 명령어를 입력하여 자바가 정상적으로 설치되었는지 확인합니다.
```shell
java --version
```

아래와 같은 메시지가 출력되면 정상적으로 설치가 완료된 것입니다.

> openjdk 17 2021-09-14 \
OpenJDK Runtime Environment (build 17+35-2724) \
OpenJDK 64-Bit Server VM (build 17+35-2724, mixed mode, sharing)


## 코드 포매팅
kotlin 코드 포매팅을 위해 Ktlint를 사용합니다.
빌드가 정상적으로 수행되지 않는 경우 아래 명령어를 통해 자동 수정을 시도합니다.
```shell
$ ./gradlew ktlintCheck
$ ./gradlew ktlintFormat
```
자동으로 코드가 수정되지 않는 경우, 포매팅에 실패한 부분을 수동으로 변경합니다.
Intellij idea IDE를 사용하는 경우 아래 명령어를 통해 ktlint의 지원을 받을 수 있습니다.
```shell
$ ./gradlew ktlintApplyToIdea
```

## Build project
```shell
$ ./gradlew clean build
```
