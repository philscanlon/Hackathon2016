# Getting Started

Ready to get started?  Follow the sections below to 

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
You should have received a mail from IT with your solace AWS access credentials.  If you have not received this, contact: Jennifer Lynn <IT@solace.com>.  The Solace login landing page for AWS is here https://sol-aws-se.signin.aws.amazon.com/console
Once you login, be sure to select the nearest home region.

### github
Create an account on Github either with your Solace email, or your own.
If you need a crash course check here: https://guides.github.com/activities/hello-world/
If you are editing the Markdown (.md) files,there is a cheatsheet here: https://guides.github.com/pdfs/markdown-cheatsheet-online.pdf

## 2. Create a VMR on Amazon Web Services

1. Login to Amazon Web Services AWS using your credentials supplied by IT
2. Select the appropriate AWS Zone before you create your VMR

Note - in the next step some of the defaults will need  to be changed for the Hackathon.

__Configuration details__
* Tags - use your name as the Tag, so you can find your own instances.
* The AMI we will use is soltr-7.2.1.616-vmr-cloud-enterprise - __ami-297dde4a__.  You can find this by going to MY AMI's and selecting :

__Ownership__ 
- [x]Shared with me.


3. Setup an AWS VMI using the guide at  http://docs.solace.com/Solace-VMR-Set-Up/Starting-VMRs-for-the-First-Time/Setting-Up-an-Eval-VMR-in-AWS.htm.
4. Enter your details in https://github.com/philscanlon/Hackathon2016/blob/master/docs/router-details.md

## 3. Configure your VMR to join the mesh
  
  1. create a VPN named hackathon
      - Export Subscriptions
      - Basic Authentication
      - Internal authentication

2. Create a CSPF neoighbor route to a core server in your region from the lsit. http://docs.solace.com/Features/Multi-Node-Routing.htm
3. Create a CSPF neighbour rour to your buddy in your region.  as above.
6. Configure your hostname and routername http://docs.solace.com/Configuring-and-Managing-Routers/Configuring-Host-Names.htm
7. Have a look at soladmin, and check you are connected


## 4. Run the samples on your local machine

Goal is to publish and subscribe to messages that follow the format: 

1.  download sample from https://github.com/SolaceSamples/
2.  Modify the publisher to .....
3.  send a message in your local language as the payload.

If you are not a developer,  dont worry - you can use the completed samples here ....

## 5. ...

## Tips
* AWS supports multiple zones, so when you login, or create an AMI, check you are in the correct region.
* AWS SE account
* AWS Charges - elastic ip - up to $4/month per ip
* AWS Charges - t2.medium - up to $8/week if left running
* AWS Charges - Data transfer rates - to confirm / estimate.  Reccomend no heavy traffic testing between zones etc.


## Tools
github - either the online web based interface, or if you have bigger things in mind, install github desktop and an editor like Atom on the Mac.

