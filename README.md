# Statement

Statement is an Android news application which uses [NewsAPI](https://newsapi.org/) to fetch news headlines from the open-source API. It is built using Kotlin and XML.

It has the following screens:

- **Splash screen**: This is displayed when the app loads up.

- **Home screen**: It has 2 tabs

  - Sources: displays a list of all news sources from the `sources` endpoint.
  - All news: displays all news from the `everything` endpoint.

- **Headlines by source**: When we choose a news source from the `Sources` screen, for example `ABC News`; we are navigated to the `Headlines by source` screen which lists all the news articles published by `ABC News`.

- **Article detail page**: This is the detail page of a news article.

<img alt="Statement Light" height="450px" src="https://github.com/RaviRawal13/statement_android/blob/main/screenshots/statement_light.gif?raw=true" />

<img alt="Statement Dark" height="450px" src="https://github.com/RaviRawal13/statement_android/blob/main/screenshots/statement_dark.gif?raw=true" />

## Development Setup

You will require latest version of Android Studio 4.1 to be able to build the application.

### API key

Head over to [NewsAPI](https://newsapi.org/register) to generate your API key. This key is needed to fetch news from the API endpoints.

- Add the generated API key to the `local.properties` file as below:

```
    apiKey = "<YOUR_API_KEY>"
```

## Application Architecture

The app uses MVVM [Model-View-ViewModel] architecture to have a unidirectional flow of data, separation of concern, testability, and a lot more.

<img alt="Flow" height="450px" src="https://github.com/RaviRawal13/statement_android/blob/main/screenshots/base_arch.png?raw=true" />

### Dependency Injection

Implemented DI using Dagger2 to provide dependencies such as shared preferences, view models, repositories, data sources to the respective consumers.

### Offline first approach

Offline first approach is used to display cached news before making API call to fetch more news from network. In case of no internet connection, only the locally cached news will be displayed.
Offline first approach is implemented using Flows. It emits events as:

```
loading -> saved data from DB -> new network data | error
```

### Pagination and Sorting

`All news` screen has pagination which is implemented using the Android paging library. It also has custom sorting(asc/desc) based on the articles `publishedAt`.

### Retrofit

Retrofit is used in the application to fetch data from the server.

## Testing

The project contains unit test cases for WebService, DAO, Repository and ViewModel.
