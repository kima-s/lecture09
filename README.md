# UserAPIについて<br>

## １、概要

- ユーザーの取得、登録、更新、削除を行うRestAPI。
- JUnitによるテストコードを実装。
    - UserMapper, UserServiceImplの単体テスト
    - 結合テスト

## 2、Userが持つ情報

| 項目      | 型      | 備考       |
|---------|--------|----------|
| id      | int    | 自動で採番される |
| name    | String |          |
| address | String |          |
| age     | int    |          |