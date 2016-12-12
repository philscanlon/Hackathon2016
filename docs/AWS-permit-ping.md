
1. login AWS console, EC2 dashboard -> choose the VMR EC2 instance, such as mine is seo-esun.
2. click on the Security Group that attached to your VMR, such as "Solace VMR Ports" etc.
3. After clicked your SG, your could see the Descrition page of your Security Group. Click on the Tab of Inbound page, click EDIT,  add a policy, Type: Custom ICMP rule, Protocol: Echo Requst, Source:0.0.0.0/0, Save.
4. Now you can ping YOUR_VMR_PUBLIC_IP from your laptop terminal now, it's the quickest way to check whether your EC2 instance up or not.
