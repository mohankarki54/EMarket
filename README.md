Project Name: EMarket

Team Members: Mohan Karki, Roshan Kandel, Sunny Raj Bhandari, Noel Tamang, Nabin Panthi

To integrate and run this project, please follow the following steps. [Note: This instruction is based on Intellij Ultimate Edition. ]

1) Download/ clone the project from the link available at the upper right corner. 
2) Open IntelliJ IDEA and close any existing project.
3) From the Welcome screen, click Import Project.
    The Select File or Directory to Import dialog opens.
4) Navigate to your Maven project and select the top-level folder. For example, with a project named emarket.
5) Click OK. The Import Project screen opens.
6) For the Import project from external model value, select Maven and click Next.
7) Select Import Maven projects automatically and leave the other fields with default values. Click Next.
8) Once the project is opened, navigate to the file application.properties which will be like: 

    spring.datasource.url= Your local sql server address
    spring.datasource.username=root
    spring.datasource.password= Password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    .
    .
    .
    spring.mail.host=smtp.gmail.com
    spring.mail.username= Your email address for sending message
    spring.mail.password=  Password
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    spring.mail.properties.mail.smtp.starttls.required=true

9) Run
10) Go to http://localhost:8080

