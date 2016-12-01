Record the details of your config in the table below.  If you have not used Github before, there are a number of steps : 

Edit file >> Click 'Propose Changes' >> Click 'Create Pull request'

Dont worry about making a mistake - your changes are reviewed before they are added into the master version.

See here for a guide on this process : https://github.com/philscanlon/Hackathon2016/blob/master/docs/How-to-add-your-router-details.md

### Core Routers  - DO NOT Change

**Router Name**|**Region Owner**|**Public ip Address**
:-----:|:-----:|:-----:
nov-core| | 
ohi-core| | 
cal-core| | 
org-core| | 
ire-core| | 
frk-core| | 
tky-core| | 
seo-core| | 
sgp-core| Phil Scanlon | 52.220.157.193
syd-core| | 
mum-core| | 
sao-core| | 

### Participant Routers - Enter your details below against your name
**Region Group**|**Name**|**AWS Region**|**Router Name**|**Router IP**|**Connection 1**|**Connection 2**
:-----:|:-----:|:-----:|:-----:|:-----:|:-----:|:-----:
01|Amit Gothecha|Asia Pacific (Mumbai)|mum-agothecha| |mumbai-core-01| 
01|Andrew Broome|US West (Oregon)|org-abroome| |oregon-core-01| 
01|Arghya Sanyal|Asia Pacific (Mumbai)|mum-asanyal| |mumbai-core-01| 
01|Benjamin Taieb|EU (Ireland)|ire-btaieb| |ireland-core-01| 
01|Christian Holtfurth|EU (Ireland)|ire-choltfurth| |ireland-core-01| 
01|Clarence Ching|Asia Pacific (Seoul)|seo-cching| |seoul-core-01| 
01|Dale Baik|US West (Oregon)|org-dbaik| |oregon-core-01| 
01|David Pochopsky|US East (N. Virginia)|nov-dpochopsky| |virginia-core-01| 
01|David Wray|EU (Ireland)|ire-dwray| |ireland-core-01| 
01|Dmitri Fedorov|US East (N. Virginia)|nov-dfedorov| |virginia-core-01| 
01|Eric Asuncion|Asia Pacific (Singapore)|sgp-easuncion| |singapore-core-01| 
01|Eric Sun|Asia Pacific (Singapore)|sgp-esun| |singapore-core-01| 
01|Eunsuk Yoon|Asia Pacific (Seoul)|seo-eyoon| |seoul-core-01| 
01|Floyd Davis|US East (N. Virginia)|nov-fdavis| |virginia-core-01| 
01|Heinz Schaffner|US East (Ohio)|ohi-hschaffner| |ohio-core-01| 
01|Ifoma Smart|US East (Ohio)|ohi-ismart| |ohio-core-01| 
01|Jet Huang|Asia Pacific (Seoul)|seo-jhuang| |seoul-core-01| 
01|Jim Lowe|US East (N. Virginia)|nov-jlowe| |virginia-core-01| 
01|John Filo|Asia Pacific (Sydney)|syd-jfilo| |sydney-core-01| 
01|Ken Barr|US East (N. Virginia)|nov-kbarr| |virginia-core-01| 
02|Ken Overton|US East (N. Virginia)|nov-koverton| |virginia-core-02| 
01|Luis Horna|US East (N. Virginia)|nov-lhorna| |virginia-core-01| 
01|Magali Boulet|EU (Frankfurt)|frk-mboulet| |frankfurt-core-01| 
01|Mahesh Kaleru|US East (Ohio)|ohi-mkaleru| |ohio-core-01| 
01|Manuel Moreno|South America (São Paulo)|sao-mmoreno| |saopaulo-core-01| 
01|Mark Spielman|US East (N. Virginia)|nov-mspielman| |virginia-core-01| 
01|Mathew Hobbis|EU (Ireland)|ire-mhobbis| |ireland-core-01| 
01|Matthew Stobo|US East (Ohio)|ohi-mstobo| |ohio-core-01| 
01|Michael Hilmen|US West (N. California)|cal-mhilmen| |ncaliforina-core-01| 
01|Naomoto Matsuki|Asia Pacific (Tokyo)|tky-nmatsuki| |tokyo-core-01| 
01|Perry Krol|EU (Frankfurt)|frk-pkrol| |frankfurt-core-01| 
01|Peter Blinstrubas|US East (Ohio)|ohi-pblinstrubas| |ohio-core-01| 
01|Ramesh Natarajan|US West (N. California)|cal-rnatarajan| |ncaliforina-core-01| 
01|Robert Hsieh|Asia Pacific (Singapore)|sgp-rhsieh| |singapore-core-01| 
01|Rodrigo Abreu|US East (Ohio)|ohi-rabreu| |ohio-core-01| 
02|Sanjeev Nagpal|US East (N. Virginia)|nov-snagpal| |virginia-core-02| 
01|Steve Chan|Asia Pacific (Singapore)|sgp-schan| |singapore-core-01| 
01|Sumeet Koshal|Asia Pacific (Singapore)|sgp-skoshal| |singapore-core-01| 
01|Tom Fairbairn|EU (Ireland)|ire-tfairbairn| |ireland-core-01| 
02|Tom O'Leary|US East (N. Virginia)|nov-to'leary| |virginia-core-02| 
01|Vincent Lam|Asia Pacific (Seoul)|seo-vlam| |seoul-core-01| 
02|Wayne Osse|US East (N. Virginia)|nov-wosse| |virginia-core-02| 
02|Wayne Sang|US East (N. Virginia)|nov-wsang| |virginia-core-02| 




| Name | Location  | Router_IP  |  RouterName |AWS Region | Notes  |
|------|-----------|------------|-------------|--------|----------|
| Aaron Lee | Hong Kong | 52.78.245.158 | hkgsolaa01 | AP NE (Seoul) | |
| David Wray | London | 52.31.220.231 | DavidHackathon   | EU (Ireland) | |
| Phil Scanlon | Singapore | 52.77.102.138  | hackathon-singapore-vmr | Asia Pacific (Singapore) | |
| Phil Scanlon | Singapore | 52.220.111.223 | pscanlon-vmr   | Asia Pacific (Singapore) | |
| Sumeet Koshal | Singapore | 52.220.165.194 | sumeet-vmr   | Asia Pacific (Singapore) | |
| Vidyadhar Kothekar | Sydney | 13.54.186.240  | Vidya-VMR-Sydney | Asia Pacific (Sydney) | |


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



