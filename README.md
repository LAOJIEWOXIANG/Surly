# SURLY Group 11

**CSCI 330 Winter 2023**

Preston Duffield, Harry Zheng, Henry Baker.

---

## Description

A single user relational database managment system.

## Project Managment

See the Trello board: [Trello Board](https://trello.com/b/cFB1usAi/surly)

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

:warning: **Make sure not to commit `.class` or any other similar files.** Add files to the gitignore if they are test/non-source.

## Setting Up a Local Environment

1. Clone the repository locally, into a `local/directory`. Note this can be any directory.
2. In a command prompt/terminal navigate to your `local/directory`.
3. Run `javac -cp src -d bin src/*.java`
   This will compile the code and create `.class` files in the `local/directory/bin` from all the `.java` files in `local/directory/src`.
4. Run `java -cp bin Main input.txt`
   This will run the program with an input file `input.txt` as an argument.

> In order to see your code changes you must recompile with the command in step 3.

:warning: **Do not commit test input text files to the git repository.** Add them to the gitignore.

## Git Procedure

Please follow the git procedure when uploading code.

Note: before step 4, if someone has committed changes to main after you created your branch, your branch is now out of sync and may have conflicts. Follow the "Out of sync with `main`" procedure.

### Procedure

1. Switch current branch to `main` and fetch orgin.
2. Create a new branch off `main` titled `yourFirstName/feature-being-worked-on`
3. Commit changes, commit early and often.
   3.5. Follow "Out of sync with 'main'" procedure if needed.
4. Open a pull request. Notify the team through discord.
5. Fix any change requests, and push to branch.
6. Merge code into `main`.

### Out of sync with `main`

1. Switch from your branch to `main`.
2. Fetch origin on main. You should now have the up to date code.
3. Switch back to your current branch.
4. Rebase your current branch with `main`.
5. Resolve conflicts locally if there are any.
6. Force push your resolved conflicts to your branch.
7. Continue to step 4 of the procedure.
