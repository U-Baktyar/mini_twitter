package twitter.source;

import twitter.configuration.ComponentMethod;
import twitter.configuration.ComponentSource;

import java.util.Scanner;

@ComponentSource
public class SideComponentSource {

    @ComponentMethod
    public Scanner scanner() {
        return new Scanner(System.in);
    }

}
