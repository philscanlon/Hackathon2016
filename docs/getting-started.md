# Getting Started

Ready to get started?  Follow the sections below to get started with the basics.

1. Create Accounts.
2. Create a VMR on Amazon Web Services
3. Configure your VMR to join the mesh
4. Run the samples on your local machine
5. ...

## Where can you get help

Americas - Emily Hong / TBD / TBD  

AsiaPac - Vidya Kothekar / Aaron Lee / Phil Scanlon  

Europe - David Wray / TBD / TBD  

## 1. Create Accounts

As part of the hackathon you will need to setup and use a couple of accounts.

### AWS
You should have received a mail from IT with your solace AWS access credentials.  If you have not received this, contact: Jennifer Lynn ( <IT@solace.com> ).

+The Solace login landing pages for AWS are:
 +AWS SE : https://sol-aws-se.signin.aws.amazon.com/console
 +AWS PSG: https://aws-psg.signin.aws.amazon.com/console

_Once you login, be sure to select the nearest home region._

### github
Create an account on Github either with your Solace email, or your own.
If you need a crash course check here: https://guides.github.com/activities/hello-world/
If you are editing the Markdown (.md) files,there is a cheatsheet here: https://guides.github.com/pdfs/markdown-cheatsheet-online.pdf

## 2. Create a VMR on Amazon Web Services

1. Login to Amazon Web Services AWS using your credentials supplied by IT
2. Select the appropriate AWS Zone before you create your VMR (if yo'ure not sure, please contact us... this information will be provided in a spreadsheet)

Note - in the online setup guide  some of the defaults will need to be changed for the Hackathon:

### Method 1

1. Once you've logged in, create an EC2 Instance
2. On the left toolbar, find "Images" and click AMIs
3. On the drop-down, select "Private Images"
4. Select the most recent Enterprise VMR (7.2.1.616)

### Method 2

1. Once you login and go to EC2, Click the Add Instance button
2. The AMI we will use is soltr-7.2.1.616-vmr-cloud-enterprise.  You can find this by going to MY AMI's and selecting : __Ownership__ 
            - [x] Shared with me.
            
### Configuration details

* Tags - use your name as the Tag, so you can find your own instances. Add HAckathon as a Tag so we can easily locate all the hackathon VMR's

1. Setup an AWS VMR using the guide at http://docs.solace.com/Solace-VMR-Set-Up/Starting-VMRs-for-the-First-Time/Setting-Up-an-Eval-VMR-in-AWS.htm.
2. Create an ElasticIP, and bind that to your VMR instance
3. Once complete, enter your details in https://github.com/philscanlon/Hackathon2016/blob/master/docs/router-details.md

## 3. Configure your VMR to join the mesh

1. Rename the hostname of your VMR, following the naming convention in the router config above.
    - http://docs.solace.com/Configuring-and-Managing-Routers/Configuring-Host-Names.htm
2. Create a global admin CLI user for you to use (don't use 'admin')... this would usually be your typical account name
3. Create a global read-only CLI user, username='ro', password='hackathon'
4. Create a Message VPN named 'hackathon'
    - Export Subscriptions
    - Basic Authentication --> Internal authentication
5. Create a client-username to use, set the password (remember it!), and enable it
6. Create a CSPF neighbor route to a core server in your region from the list. http://docs.solace.com/Features/Multi-Node-Routing.htm
7. Create a CSPF neighbor rour to your buddy in your region.  as above.
8. Using SolAdmin, connect to either your public IP or ElasticIP address

To check if you can send messages to the mesh, visit this page : http://52.220.223.99/TopicSubscriber.html.  Any messages you publish should be reflected here.


## 4. Run the samples on your local machine

Goal is to publish and subscribe to messages that follow the format: 

`topicname : topic/geo`  
`content : {"lat":"22.123456","lng":"118.123456","name":"Test","otherAttributes":"whateverYouWant"}`

The payload should be in binary format.

1.  Download sample from https://github.com/SolaceSamples/
2.  Modify the publisher to send to the hackathon mesh
3.  Send a message with the above format as the payload

If you are not a developer,  sdkperf can also be used:

1.  Save the payload to a file
2.  Use the -pal option to sdkperf to send a message with the contents of the file as a binary attachment

## Tips
* AWS supports multiple zones, so when you login, or create an AMI, check you are in the correct region if you cannot find your instance
* AWS Charges - elastic ip - up to $4/month per ip
* AWS Charges - t2.medium - up to $8/week if left running
* AWS Charges - Data transfer rates - please check before running load across the network - no heavy traffic testing between zones.

## Frequest Gotcha's

Area | Error | What's missing
:-----:| :-----: | :-----: | 
MNR | | |


## Tools
github - either the online web based interface, or if you have bigger things in mind, install github desktop and an editor like Atom on the Mac.

