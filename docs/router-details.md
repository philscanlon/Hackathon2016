Record the details of your router configuration in the table below.  If you have not used Github before, there are a number of steps to propose a change to this file : 

Edit file >> Click 'Propose Changes' >> Click 'Create Pull request'

Dont worry about making a mistake - your changes are reviewed before they are added into the master version.  If your change is not reflected within 12 hours, please contact your regional Lead.  The most common mistake is skipping the ast step: 'Create Pull Request'.

See here for a guide on this process : https://github.com/philscanlon/Hackathon2016/blob/master/docs/How-to-add-your-router-details.md

### Core Routers  - DO NOT Change

**Router Name**|**Number of Participants**|**Owner**|**IPAddress**|**CORE Connection 1**|**Core Connection 2**|**Core Connection 3**
:-----:|:-----:|:-----:|:-----:|:-----:|:-----:|:-----:
virginia-core-01|7|Emily| | | | 
virginia-core-02|7|Emily| | | | 
seoul-core-01|6|Aaron| | | | 
ohio-core-01|5|Dishant| | | | 
ireland-core-01|5|David| | | | 
ncaliforina-core-01|4|Dishant| | | | 
frankfurt-core-01|4|David| | | | 
singapore-core-01|4|Phil| 52.220.157.193 | | | 
sydney-core-01|1|Vidhya| | | | 

### Participant Routers - Enter your details below against your name

Add your Router IP in the table. your first connection is mandatory, and will connect you to the core set of servers in the various AWS Regions. Your second connection should be to a peer in your region.

Note - We have not put core routers in all the regions.   Your router that you create and and the routers you mesh with do not have to be in the same AWS region.

**Name**|**AWS Region**|**Router Name**|**Router IP**|**Connection 1**|**Connection 2**
:-----:|:-----:|:-----:|:-----:|:-----:|:-----:
Amit Gothecha|EU (Frankfurt)|frk-agothecha| |frankfurt-core-01| 
Andrew Broome|US East (N. Virginia)|nov-abroome| |virginia-core-01| 
Arghya Sanyal|EU (Frankfurt)|frk-asanyal| |frankfurt-core-01| 
Benjamin Taieb|EU (Ireland)|ire-btaieb| |ireland-core-01| 
Christian Holtfurth|EU (Ireland)|ire-choltfurth| |ireland-core-01| 
Clarence Ching|Asia Pacific (Seoul)|seo-cching| |seoul-core-01| 
Dale Baik|US West (N. California)|cal-dbaik| |ncaliforina-core-01| 
David Wray|EU (Ireland)|ire-dwray| |ireland-core-01| 
David Pochopsky|US East (N. Virginia)|nov-dpochopsky| |virginia-core-01| 
Dmitri Fedorov|US East (N. Virginia)|nov-dfedorov| |virginia-core-01| 
Eric Sun|Asia Pacific (Seoul)|seo-esun| |seoul-core-01| 
Eric Asuncion|Asia Pacific (Singapore)|sgp-easuncion| |singapore-core-01| 
Eunsuk Yoon|Asia Pacific (Seoul)|seo-eyoon| |seoul-core-01| 
Floyd Davis|US East (N. Virginia)|nov-fdavis| |virginia-core-01| 
Heinz Schaffner|US East (Ohio)|ohi-hschaffner| |ohio-core-01| 
Ifoma Smart|US East (N. Virginia)|nov-ismart| |virginia-core-01| 
Jet Huang|Asia Pacific (Seoul)|seo-jhuang| |seoul-core-01| 
Jim Lowe|US East (N. Virginia)|nov-jlowe| |virginia-core-01| 
John Filo|Asia Pacific (Sydney)|syd-jfilo| |sydney-core-01| 
Ken Barr|US East (N. Virginia)|nov-kbarr| |virginia-core-02| 
Ken Overton|US East (N. Virginia)|nov-koverton| |virginia-core-02| 
Luis Horna|US East (N. Virginia)|nov-lhorna| |virginia-core-01| 
Magali Boulet|EU (Frankfurt)|frk-mboulet| |frankfurt-core-01| 
Mahesh Kaleru|US East (Ohio)|ohi-mkaleru| |ohio-core-01| 
Manuel Moreno|US West (N. California)|cal-mmoreno| |ncaliforina-core-01| 
Mark Spielman|US East (N. Virginia)|nov-mspielman| |virginia-core-02| 
Mathew Hobbis|EU (Ireland)|ire-mhobbis| |ireland-core-01| 
Matthew Stobo|US East (Ohio)|ohi-mstobo| |ohio-core-01| 
Michael Hilmen|US West (N. California)|cal-mhilmen| |ncaliforina-core-01| 
Naomoto Matsuki|Asia Pacific (Seoul)|seo-nmatsuki| |seoul-core-01| 
Perry Krol|EU (Frankfurt)|frk-pkrol| |frankfurt-core-01| 
Peter Blinstrubas|US East (Ohio)|ohi-pblinstrubas| |ohio-core-01| 
Ramesh Natarajan|US West (N. California)|cal-rnatarajan| |ncaliforina-core-01| 
Robert Hsieh|Asia Pacific (Singapore)|sgp-rhsieh| |singapore-core-01| 
Rodrigo Abreu|US East (Ohio)|ohi-rabreu| |ohio-core-01| 
Sanjeev Nagpal|US East (N. Virginia)|nov-snagpal| |virginia-core-02| 
Steve Chan|Asia Pacific (Singapore)|sgp-schan| |singapore-core-01| 
Sumeet Koshal|Asia Pacific (Singapore)|sgp-skoshal| |singapore-core-01| 
Tom Fairbairn|EU (Ireland)|ire-tfairbairn| |ireland-core-01| 
Tom O'Leary|US East (N. Virginia)|nov-to'leary| |virginia-core-02| 
Vincent Lam|Asia Pacific (Seoul)|seo-vlam| |seoul-core-01| 
Wayne Osse|US East (N. Virginia)|nov-wosse| |virginia-core-02| 
Wayne Sang|US East (N. Virginia)|nov-wsang| |virginia-core-02| 

# Link Costs:

<b>Note: should be symmetric!!</b>
How to enforce??

| Between | And | Cost |
|---------|-----|------|
| pscanlon-vmr | hkgsolaa01 | 30 |
| hkgsolaa01 | DavidHackathon | 100 |

# Config

Rather than just use the defaults, we could tune a few things... practice!

 - Enable compression on your neighbour links
   - Ensure port 55003 is exposed in AWS
 - Tune TCP params:
   - max-wnd = 1024 (1MB)
   - initial-cwd = 10
 - Tune the CSPF NAB queues if you really want
   - max-length = 40000
   - min-burst = 500



