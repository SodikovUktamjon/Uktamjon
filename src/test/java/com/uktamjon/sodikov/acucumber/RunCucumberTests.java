package com.uktamjon.sodikov.acucumber;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", glue = "com/uktamjon/sodikov/acucumber")
public class RunCucumberTests {
}
