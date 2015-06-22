import com.atlassian.selenium.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RenderedRemoteWebElement;
import org.openqa.selenium.server.browserlaunchers.GoogleChromeLauncher;


import java.util.Random;

public class Main {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        loginToTwitter(driver);
        tweetToTwitter(driver);
        followSomebody(driver);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();

    }

    private static void followSomebody(WebDriver driver) {

        try {
            WebElement followButton = driver.findElement(By.xpath("//*[@id=\"empty-timeline-recommendations\"]/div/div[2]/ul/li[1]/div/div/button"));
            followButton.click();
        } catch (NoSuchElementException e) {
            System.out.println("No main follow button, all recommended users were followed. Trying to find another follow button...");
        }
        try {
            WebElement smallFollowButton = driver.findElement(By.cssSelector("#page-container > div.dashboard.dashboard-left > div.module.roaming-module.wtf-module.js-wtf-module.has-content > div:nth-child(1) > div.js-recommended-followers.dashboard-user-recommendations.flex-module-inner > div:nth-child(1) > div.content > div > button"));
            smallFollowButton.click();
        } catch (Exception e) {
            System.out.println("Follow test failed: " + e.getMessage());
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            WebElement followIcon = driver.findElement(By.cssSelector(".Icon.Icon--small.Icon--follower"));
            System.out.println("Follow user test passed");
        } catch (Exception e){
            System.out.println("Follow user test failed - no follow icon");
        }
    }

    private static void tweetToTwitter(WebDriver driver) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement tweetBox = driver.findElement(By.id("tweet-box-home-timeline"));
        String tweetText = "Test tweet" + generateString(new Random(), "1234567890", 10);
        tweetBox.sendKeys(tweetText);

        String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
        tweetBox.sendKeys(keysPressed);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            WebElement tweetBlock = driver.findElement(By.className("js-stream-item"));
            String latestTweetValue = ((RenderedRemoteWebElement) tweetBlock).findElementByClassName("js-tweet-text").getText();
            if (tweetText.equals(latestTweetValue)) {
                System.out.println("Tweet test passed");
            } else {
                System.out.println("Tweet test failed");
            }
        } catch (Exception e) {
            System.out.println("Tweet test failed " + e.getMessage());
        }
    }

    private static void loginToTwitter(WebDriver driver) {
        driver.get("http://twitter.com");
        WebElement email = driver.findElement(By.id("signin-email"));
        email.sendKeys("luke.skywalker2015@yandex.ru");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement password = driver.findElement(By.id("signin-password"));
        password.sendKeys("skywalker2015");
        password.submit();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            WebElement profileBlock = driver.findElement(By.xpath("//*[@id=\"page-container\"]/div[1]/div[1]/div"));
            System.out.println("Login test passed");
        } catch (Exception e) {
            System.out.println("Login test failed");
        }
    }

    public static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

}
