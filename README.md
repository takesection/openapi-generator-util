![build workflow](https://github.com/takesection/openapi-generator-util/actions/workflows/gradle.yml/badge.svg)

OpenAPI Generator ユーティリティ
===

# ビルドと使用方法

```
gradlew clean build shadowJar
java -jar build/libs/openapi-generator-util-0.1.0-all.jar download init generate
```

# 使い方

OpenAPI の定義ファイル (例: spec/openapi.yml) と OpenAPI Generator のための設定ファイル (build.yml) が必要です。

build.yml ファイルには、次のような項目を設定します。

| 項目名 | 説明                                                                                    |
|---|---------------------------------------------------------------------------------------|
| version | build.yml のスキーマバージョンです。'1.0' を設定してください。                                               |
| openAPIGeneratorVersion | 使用する OpenAPI Generator のバージョンを指定します。                                                  |
| javaBasePackage | Spring など Java のコードを生成する場合のベースとなるパッケージ名です。パッケージ名に使用できない文字等を含んでいる場合は、ユーティリティはエラーを返します。 |
| openAPISpec | OpenAPI の定義ファイルのパスです。 |
| properties | 上記に加えて、OpenAPI Generator の設定ファイルを生成するためのプロパティを設定します。|
| modules | OpenAPI Generator でコードを生成するための設定ファイル名、OpenAPI Generator 実行時のコマンドラインオプション、設定ファイルのテンプレートを設定します。 |

## properties

modules のテンプレート (template) では、プロパティで設定された値を利用することができます。プロパティ名は、`{{` と `}}` で囲みます。

プロパティには、環境変数も含みます。

さらに特別なプロパティが以下のようにあります。

| プロパティ名 | 説明 |
|---|---|
| openAPISpec | OpenAPI の定義ファイルのパスです。|
| specVersion | OpenAPI の定義ファイルに書かれた API バージョン (info.version の部分) です。|
| javaBasePackage | Spring など Java のコードを生成する場合のベースとなるパッケージ名です。|
| now | このユーティリティを実行時のタイムスタンプ (フォーマットは 年月日時分秒 です。例: 20221231235959) です。|

## コマンド

コマンドは、`download`、`init`、`generate` の3つです。

デフォルトでは、ユーティリティが利用する設定ファイルは、`build.yml` です。これは、`-f ファイル名` で変更することができます。

### download

ユーティリティ設定ファイル (デフォルトでは、build.yml) で指定されたバージョンの OpenAPI Generator のバイナリパッケージをダウンロードします。デフォルトでは、Maven Central (https://repo1.maven.org/maven2) から取得します。

独自の Maven Repository (Sonatype Nexus 等) からダウンロードする場合は、環境変数 "MAVEN_REPOSITORY_URL" またはシステムプロパティ "MAVEN_REPOSITORY_URL" で指定することができます。

### init

ユーティリティ設定ファイル (デフォルトでは、build.yml) のテンプレートから OpenAPI Generator でコード生成を実行するための設定ファイルを生成します。

### generate

`init` コマンドで生成された設定ファイルを使って、OpenAPI 定義ファイルに書かれた仕様のコードを OpenAPI Generator を使って生成します。
