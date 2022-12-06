# Cruel-App-Console-Batch

## 배치 실행
배치 어플리케이션에서 여러개의 Job을 제공하기 때문에 실행시 Job 이름과 JobParameter를 인자로 넘겨야 합니다.

```shell
$ java -jar application.jar --job.name=notifyApproachingRepayJob baseDate=2022-12-06
```
