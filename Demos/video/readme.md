
# During the hackathon you can receive a live video stream

Download the project here: https://github.com/roberthsieh/broadcastme

## Simple Hackathon instructions

1. Download Project from link above
2. Build the output proxy: gradlew assemble
3. Start the output Proxy: ./build/staged/bin/outputProxy <your_vmr_ip>:55555 default default channel/phil 127.0.0.1 1239 verbose
4. Start VLC and open a network UDP stream with: udp://@127.0.0.1:1239
