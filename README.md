Application Notes

 - Every page has a scrollView, it is recommended to scroll if the screen is small.
 - When the Buy button is pressed, it goes to the second screen.
 - The values ​​coming through the API and socket are placed randomly. The purpose is to show that the socket and suddenly incoming data are updated.
 - Data coming through the socket is updated every 3 seconds.
 - On the first page, data from the API was set in the price and quantity sections.
 - I have observed that sometimes there is no change in the socket given for Candles, and sometimes the data numbers may decrease. For your information.
 - The network layer is integrated.
 - Seekbar is operational.
 - Written in MVVM and using Kotlin.
 - Loading layout was used. It is displayed until the response comes.
 - Since an error was received in the svg format of some images via Figma, an example was put in svg or png format. (in navigation bar like Fabs)
 - Null checking is done throughout the application.
 - The back button on the second page works.
