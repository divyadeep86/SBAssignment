
# Assigment 

Android app which communicates with the public Github API to display
information about a specific user.

## Project Overview 

This project is an Android application designed to display GitHub user information and their repositories. It follows the Clean Architecture principles, dividing the codebase into three primary layers: Data, Domain, and UI. This separation enhances the maintainability, testability, and scalability of the application.

## Coding Pattern

The project adopts the MVVM (Model-View-ViewModel) pattern for its architectural foundation, facilitating a clear separation between the application's user interface and the business logic. This pattern is particularly well-suited for handling lifecycle events and UI state management in Android development.

## Documentation:Data, Domain, and UI Layers



#### Overview

In a typical Android application following clean architecture principles, the codebase is divided into three primary layers: Data, Domain, and UI. Each layer has distinct responsibilities and interacts with the others through well-defined interfaces. This separation enhances modularity, testability, and maintainability.

### Data Layer   
Responsibilities
- Manages application data (remote and/or local).
- Implements API calls and database operations.
- Transforms network or database models to data models suitable for the domain layer.

#### Key Classes
- **UserRepository**: Acts as the single source of truth for user data. It fetches data from the UserApi and handles data persistence if needed.
- **UserApi**: Defines Retrofit interfaces for making network requests.
- **UserDao**: (Not shown) Would interface with the local database for data persistence.


### Domain Layer  
Responsibilities
- Contains business logic.
- Defines use cases that represent actions the application can perform.
- Operates on domain models, which are agnostic of the data source and UI.

#### Key Classes
- **GetUserReposUseCase**: Fetches a list of repositories for a given user. It uses UserRepository to retrieve data and applies any necessary business logic.
- **UserInfoUseCase**: Retrieves detailed information about a user. Similar to GetUserReposUseCase, it leverages UserRepository.

### UI Layer 
Responsibilities
- Presents data to the user and captures user inputs.
- Observes changes in the ViewModel and updates the UI accordingly.
- Navigates between screens and manages UI state.

#### Key Classes
- **UserInfoViewModel**: Manages the UI-related data for displaying user information. It interacts with UserInfoUseCase.
- **UserReposViewModel**: Manages the UI-related data for displaying a list of user repositories. It - interacts with GetUserReposUseCase.
- **UserInfoScreen, UserRepoDetailsScreen**: Composable functions that define the UI for displaying user information and repository details, respectively.

### Interaction Between Layers
- **UI -> Domain**: The UI layer invokes use cases from the domain layer to perform actions or retrieve data. For example, UserInfoViewModel calls UserInfoUseCase.getUserInfo().
- **Domain-> Data**: Use cases interact with the data layer, specifically repositories, to fetch or manipulate data. UserInfoUseCase uses UserRepository to get user information.
- **Data -> Domain**: The data layer returns data to the domain layer, often transforming it into domain models. UserRepository fetches data from UserApi and returns it as domain models to UserInfoUseCase.
- **Domain -> UI**: Once the domain layer processes the data, it's passed back to the UI layer, where it's displayed to the user. UserInfoUseCase provides user information to UserInfoViewModel, which updates the UI.

## Additional Patterns and Practices
- **Dependency Injection**: Utilizes Hilt for dependency injection, simplifying the process of providing dependencies to various components of the application.
- **State Management**: Employs state handling within ViewModels to manage UI state and data, ensuring a reactive and responsive user experience.
- **Asynchronous Programming**: Leverages Kotlin Coroutines and Flow for handling asynchronous operations and real-time data updates, making the app more efficient and user-friendly.
- **Composable Functions**: Uses Jetpack Compose for building the UI, adopting a declarative approach to UI development that results in more concise and readable code.




