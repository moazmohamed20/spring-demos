# SOAP Demo using WSDL File

## Project Setup (Done Once For Every Project)

1. Dependencies

   ```xml
   <!-- Web Services -->
   <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-web-services</artifactId>
   </dependency>
   ```

2. WSDL to Java Class

   ```shell
   wsimport src/main/resources/static/Dialer.wsdl -keep -s src/main/java -d target/classes
   ```

   or

   ```xml
   <build>
       <plugins>
         <!-- ... -->
         <!-- WSDL to Stub -->
         <plugin>
           <groupId>com.sun.xml.ws</groupId>
           <artifactId>jaxws-maven-plugin</artifactId>
           <version>2.3.7</version>
           <executions>
             <execution>
               <goals>
                 <goal>wsimport</goal>
               </goals>
             </execution>
           </executions>
           <configuration>
             <wsdlFiles>
               <wsdlFile>${project.basedir}/src/main/resources/static/Dialer.wsdl</wsdlFile>
             </wsdlFiles>
             <keep>true</keep>
             <sourceDestDir>src/main/java</sourceDestDir>
             <destDir>target/classes</destDir>
           </configuration>
         </plugin>
         <!-- ... -->
       </plugins>
     </build>
   ```

3. Create Config Class

   ```java
   @EnableWs
   @Configuration
   public class WebServiceConfig {

     @Bean
     ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
       MessageDispatcherServlet servlet = new MessageDispatcherServlet();
       servlet.setApplicationContext(context);
       servlet.setTransformWsdlLocations(true);
       return new ServletRegistrationBean<>(servlet, "/ws/*");
     }

     // http://localhost:8080/ws/dialer.wsdl
     @Bean(name = "dialer")
     Wsdl11Definition defaultWsdl11Definition() {
       SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
       wsdl11Definition.setWsdl(new ClassPathResource("/static/Dialer.wsdl"));
       return wsdl11Definition;
     }

   }
   ```

## Now, Let's The Party Begin
