# Repository Scoring System

A balanced approach to evaluating GitHub repositories based on popularity, community adoption, and recent activity.

## Features
- Fetches repositories from GitHub using a public search endpoint
- Configurable filters for the earliest creation date and the repository language
- Assigns a popularity score to each repository based on multiple factors
- API endpoints for easy integration with other systems

## Installation

1. Clone the repository
2. Make sure you have Java 21+ and Gradle installed
3. Update `github-token` in **application.yaml** with your personal token. [How to create a fine-grained personal access token ](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-fine-grained-personal-access-token).
3. Run `./gradlew build` to build the application

## Running the Application

```bash
./gradlew bootRun
```

Once started, the application will be available at:
- API: http://localhost:8080/
- Swagger UI: http://localhost:8080/swagger-ui/index.html

## How It Works

The scoring formula is inspired by the **Human Development Index (HDI)** and uses a **geometric mean-like approach** to balance three key factors:

### 1. Stars ⭐
Stars represent the number of users who have bookmarked the repository.

### 2. Forks 🍴
Forks indicate how many developers have copied the repository to contribute or adapt it.

### 3. Recency 🕒
How recently the repository was updated, with more recent updates scoring higher.

## Scoring Formula

The system normalizes and combines these factors using the following approach:

### Normalization

* **Stars and forks are capped and normalized** to avoid extreme values dominating the score:

```
StarsNorm = min(stars, MAX_STARS) / MAX_STARS
ForksNorm = min(forks, MAX_FORKS) / MAX_FORKS
```

* **Recency decays exponentially** based on months since the last update:

```
RecencyNorm = exp(-months_since_last_update / RECENCY_DECAY_CONSTANT)
```

### Constants

* **MAX_STARS = 200,000** – Cap for the number of stars
* **MAX_FORKS = 50,000** – Cap for the number of forks
* **RECENCY_DECAY_CONSTANT = 12** – Represents a half-life of one year (12 months)

### Final Score Calculation

The final score uses a geometric mean-like formula:

```
Score = (StarsNorm * ForksNorm * RecencyNorm)^(1/3)
```

## Configuration Options
- `created_date`: The earliest creation date for repositories (e.g., `2020-01-01`)
- `language`: The programming language of the repositories (e.g., `Java`, `Python`)

## API Endpoints

### `GET /api/v1/repos`
Fetches and returns a list of repositories along with their popularity scores.

#### Query Parameters:
- `created_date` (optional): Filter repositories created after this date
- `language` (optional): Filter repositories by language

#### Response Example:
```json
[
  {
    "name": "example-repo",
    "stars": 150,
    "forks": 45,
    "last_updated": "2025-02-10T12:00:00Z",
    "score": 0.675
  }
]
```