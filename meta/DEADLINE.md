# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice looking HTML.

## Part 1: App Description

> Please provide a firendly description of your app, including the app
> category that you chose, and the primary functions available to users
> of the app.

I have created an external API tool called "PokePod" which retrieves the Astronomy Picture of the Day (APOD) for a specfic date, looks through the pixels in the picture to determine the dominant color in the picture (excluding black, grey, and brown because those colors overwhelm astronomy pictures and do not look interesting), and then, using that color, returns three random pokemon that are classfied as that same color using PokeApi. For example, if an APOD has a lot of pink, the app will display the APOD of that day next to 3 pink pokemon. When using the app, the user can hit a button at the top of the window to trigger a popup box where they can enter a date, which will be used to retrieve the APOD and then the pokemon. There is a message bar below the button which will also tell you what the dominant color in the image is (it can be surprising sometimes!) and the date that the APOD is from.

    Some particularly pretty dates to try out for specific colors are:

    Red:    2005-04-03
    Blue:   2021-09-23
    Yellow: 2019-04-20
    Green:  2019-07-14
    Purple: 2007-09-21
    White:  2010-01-17
    Pink:   2002-12-20

## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?

It was really fun trying to figure out how to get the dominant color in an image. I was surprised to learn that there wasn't a method I could call or a class that I could import that would help me solve this problem. I used PixelFormat to get the rgb value for a certain pixel, had to convert the integer RGB to hex code, and then normal rgb which I stored in an array, and then I made a method to find the distance between my rgb value and reference rgb values to map the rgb value to a String name. Then, I went through a proportion of the pixels in my image to determine which color showed up the most frequently. It was really fun to figure out this whole process, and I think that I may be interested in this side of coding more than I was interested in the graphics/javafx side of coding. I also learned a lot about passing objects around between classes, in particular, I faced difficulties with trying to use static variables in other classes.

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

I would have spent much more time on planning, there is a lot more planning implementing a project from scratch than there is following a project, and because of this difference, I had difficulties organizing my classes and methods. Further, I only realized in hindsight how much easier this entire project would have been for me if I had taken advantage of inheritance. I would also try to incorporate more break time in my coding process. At some point, I spent several hours dealing with a bug, but took a break, and when I sat down, I was immediately able to resolve the issue.
