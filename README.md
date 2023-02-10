[![License: GPL v3](https://img.shields.io/badge/License-GPL_v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

## Demo application for Microsoft Graph API Sharepoint file upload ##

### Description ###

This sample is a working application to test and demonstrate how to upload files to a sharepoint site using the Graph Rest API. The authentication is done using MSAL4J library, and the file upload with REST requests (upload session).

### Requirements ###
* JDK 17
* Registered application on Azure AD tenant (with a shared secret), see [Quickstart: Register an application with the Microsoft identity platform](https://learn.microsoft.com/en-us/azure/active-directory/develop/quickstart-register-app)
* Permissions Files.ReadWrite.All or Sites.Selected

### Configuration ###

The application.conf needs to be populated with information so the application can connect to the mailbox:

* ClientId (aka ApplicationId)
* ClientSecret
* Tenant domain name
* OAuth Scope (preconfigured with https://graph.microsoft.com/.default)
* Sharepoint site (like contoso.sharepoint.com)
* Graph API url (preconfigured with https://graph.microsoft.com/v1.0)

### Notes ###

* This demo is using ***client_credentials*** grant type.
* The upload is done using upload session, as described in the document: [Upload large files with an upload session](https://learn.microsoft.com/en-us/graph/api/driveitem-createuploadsession?view=graph-rest-1.0).

### Usage ###

* Clean
    ~~~
    ./gradlew clean
    ~~~

* Run
    ~~~
    ./gradlew run
    ~~~

* Create distribution (on build/install)
    ~~~
    ./gradlew installDist
    ~~~

* Create distribution zip (on build/distributions)
    ~~~
    ./gradlew distZip
    ~~~

* Run from build/install
  ~~~
  ./demo-sharepointupload-rest.bat file-to-upload
  ~~~
  or
  ~~~
  java -jar lib/demo-sharepointupload-rest-0.1.jar file-to-upload
  ~~~

When the application is executed, if an application.conf doesn't exist, it will be created from an internal template. See [Configuration](#configuration).

### License ###
Copyright © 2023, [Picture Soluções em TI](https://www.picture.com.br)

This demo application is licensed under [GPLv3](https://www.gnu.org/licenses/gpl-3.0), see [LICENSE](LICENSE).