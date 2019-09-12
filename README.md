# Skystone
Latin Robotics 2019-2020 season

Jamir = Amir or Jackson


Why use github?
1. We've had code randomly vanish off the phone, thats not good. Github prevents "Disney Pixar's Finding auto.java"

2. Version control, lets us separate previous code, working code, and in progress code

3. easy to access in the future

4. forces us to be organized

5. if you like to code, u will probably use it in the future

6. sounds cool to judges (i think?)

How to set up GitHub for Latin School Robotics
0. Mac needs to download xcode. Type "xcode-select --install" in terminal

1. Follow these instructions
	https://help.github.com/en/articles/set-up-git

1.5. Set up an ssh key on ur computer and link it to github, use this link
https://help.github.com/en/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent#generating-a-new-ssh-key
if you have one already add it to ur github account

2. Go to the repository link:
	Click the green "clone button" than than click "use ssh" 

3. ```git init``` in terminal

4. ```git clone``` than paste the thing from github.

5. I probably forgot something in this, just contact Jamir if problems arise 


Working with GitHub


0. Make sure you've followed previous steps correctly

1. Update master branch
	```git checkout master``` than 
      	```git pull origin master```

2. Make new branch
	```git checkout -b "name of branch" follow naming conventions below```

3. Go to branch and update it if not up to date
	```git checkout (name of branch)
	git merge master
	git commit
	git push```

4. ```git checkout (name of branch)```

5. Edit files with whatever

6. ```git status``` to see what files are different from master branch

7. ```git add (file to commit)``` do this for every file u want to change

8. ```git commit -m "commit msg"```, follow instructions below"

9. ```git push origin (name of branch)```

10. Go to repository on github.com

11. Should see a green message asking to compare and make pull request, if not u messed up

12. Follow steps online to make the pull request, describe what the code is doing and be prepared for a live test with Jamir 



Branch naming convention: 
	Name(Auto or Teleop)
	Example: Amir(Auto)
 	Dont use spaces
	
Commit naming convention:
	type: description of change
	type = feat || refactor || fix || chore (google or ask Jamir if you don't know these words, preferably google)
	Don't make it long, work on being concise with your language
	Write in imperative present tense (add function to x instead of added function to x)
	Example: feat: robot grab ball

w0w! You can now use GitHub, not so scary

Terms:
Master branch: The code we know works, all branches derived from this
branch: clone of the master branch that you create to make changes on without changing the master branch
commit: a commit is when you bring changes from your computer to the branch you're working on while is stored in the cloud (w0w)
pull request: request to merge code from your branch to the master branch, requires code to be validated as working before it is merged

Contacting Jamir:
Amir:
adarbar@lsoc.org
Slack if we set it up
if u rlly want my phone number ill give it to u
words

Jackson:
jbremen@lsoc.org
Slack if we set it up
if u rlly want my phone number ill give it to u
words
