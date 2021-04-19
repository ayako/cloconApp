# CloconApp

## Simple android app template for products items posting | listing with user profile stored in database.

## Layout (User interface)

- Simply designed using Activity (no Fragment used)
  - Signin: using email for profile data table key
  - Main: using RecyclerView for item listing
  - Item: create | update item info
  - Profile: create | update user profile
  - Menu

### Data

- Product item and user profile data stored in local SQLite database (instead of shared database)
- Profile images stored in local storage (instead of shared storage)
- Should Item images stored in public storage (and save its url into database)
