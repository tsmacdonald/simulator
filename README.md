![Universal Simulator Logo](/src/images/UniSIMLogo.png)

Created by Wheaton College's Spring 2013 Software Development class.

##Description

Welcome to UniSIM. This program provides the user with a blank slate (a grid), and all the tools that he or she needs to populate that grid with "agents," which can be anything from a rabbit in a predator-prey simulation, to "alive" squares in Conway's Game of Life. In addition, the user can specify interactions between these objects, called "triggers," and the outcome of those interactions ("behaviors"). Ultimately, this provides the user with a sandbox to create almost any interaction possible for a grid-based system.

##How to Contribute

1. Fork this repository
  * Click *Fork* in the upper right
2. Make changes and contributions to the forked repository
  * Clone the repository to your computer with <code>git clone https://github.com/username/simulator.git</code>
  * See below for steps on importing the project into Eclipse
  * Commit changes and push them to the forked project
  * If you need to add changes that have been made to the original repository, use <code>git pull --rebase upstream/master</code>
3. Finally, use a [pull request](https://help.github.com/articles/using-pull-requests) to add your code to the original repository.

##Eclipse Details

To add the project to Eclipse:  
File -> Import -> General -> Existing Projects into Workspace  
Select the repository directory, then click "Finish"  

To import the Coding Standard into Eclipse:  
Window -> Preferences -> Java -> Code Style -> Formatter -> Import  
Select the CodingStandard.xml file from the root directory of the repository and click "Apply" and "OK"
