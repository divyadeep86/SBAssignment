
# Assigment 

Android app which communicates with the public Github API to display
information about a specific user.


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

This architecture ensures a clear separation of concerns, making the code easier to understand, test, and maintain.
