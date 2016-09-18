// from http://diwakergupta.github.io/thrift-missing-guide/

namespace java com.github.iamsteveholmes

enum TweetType {
    TWEET = 1,
    RETWEET = 2,
    DM = 3,
    REPLY = 4
}

struct Tweet {
    1: required i32 userId;
    2: required string userName;
    3: required string text;
    4: optional string loc;
    5: optional TweetType tweetType = TweetType.TWEET
    16: optional string language = "english"
}
