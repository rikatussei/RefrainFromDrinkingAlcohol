# Refrain From Drinking Alcohol「飲酒を控え隊」

## 概要
禁酒をサポートするアプリケーションです。  
記録や目標設定を行うことで、禁酒を達成しやすくする機能を提供します。

## 目的１
- 禁酒に取り組む人々を支援します。
- 達成感を感じやすい仕組みでモチベーションを維持します。
- アルコール消費に関する習慣改善をサポートします。

## 目的２
-以前開発をしたtrouble_solver_navigator「トラブル解決ナビゲーター」で発生した複数のエラーの原因を発見する為、より初歩的な技を習得します。

## 使用技術
- **フロントエンド**：HTML, CSS, JavaScript
- **バックエンド**：Java
- **データベース**：PostgreSQL
- **インフラ**：Docker（予定） or AWS

## セットアップ手順
1. このリポジトリをクローンします：
   ```bash
   git clone https://github.com/rikatussei/RefrainFromDrinkingAlcohol.git
   cd RefrainFromDrinkingAlcohol
   
## 環境設定

1. `src/main/resources/config.properties.sample` をコピーし、`src/main/resources/config.properties` にリネームします。
2. `config.properties` 内の `openai.api.key` に、実際のAPIキーを設定します。
3. 必要に応じて、その他の設定値（`model`, `max-tokens`, `temperature`）を更新します。
