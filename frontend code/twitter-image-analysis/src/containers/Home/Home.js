import React from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import TweetInput from "../../components/TweetInput";
import { logout, getUserById } from "../../modules/users";
import { createTweet, getTweetById, getAllTweets } from "../../modules/tweets";
import Tweet from "../../components/Tweet";
import Timeline from "../../components/Timeline";
import sortByDatetime from "../../utils/datetime";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { Client, Message  } from '@stomp/stompjs';
import { JMS_USERNAME, JMS_PASSWORD, SUBSCRIBER_QUEUE, PUBLISH_QUEUE, BROKER_URL, TYPE } from "../../utils/Constants"

export class Home extends React.Component {
  sendTweetInterval = null;
  client = null;
  static propTypes = {
    activeUser: PropTypes.shape({
      id: PropTypes.string.isRequired,
      username: PropTypes.string,
    }).isRequired,
    tweets: PropTypes.arrayOf(PropTypes.object.isRequired).isRequired,
    createTweet: PropTypes.func.isRequired,
  };

  initialQueueSetup(){
    this.client = new Client();
    this.client.configure({
      brokerURL: BROKER_URL,
      connectHeaders: {
        login: JMS_USERNAME,
        passcode: JMS_PASSWORD,
      },
      // Helps during debugging, remove in production
      // debug: (str) => {
      //   console.log(new Date(), str);
      // }
    });
    this.client.activate();
  }
  
  subscribeFromQueue(queue) {
    this.client = new Client();
    this.client.configure({
      brokerURL: BROKER_URL,
      connectHeaders: {
        login: JMS_USERNAME,
        passcode: JMS_PASSWORD,
      },
      onConnect: () => {
        this.client.subscribe(queue, message => {
          if (message.body) {
          const text = JSON.parse(message.body).annotatedImage;
          const identifiedObject = JSON.parse(message.body).identifiedObject;
          const tag = JSON.parse(message.body).tag;
          const {
            createTweet,
            activeUser: { id: userId },
          } = this.props;
          createTweet({ userId, text, identifiedObject, tag });
        } else {
          console.log("Empty message");
        }
        });
      },
      // Helps during debugging, remove in production
      debug: (str) => {
        console.log(new Date(), str);
      }
    });
    this.client.activate();
  }

  componentDidMount() {
    this.initialQueueSetup();
    // this.subscribeFromQueue(SUBSCRIBER_QUEUE);
  }

  publishMessage(message) {
    // trying to publish a message when the broker is not connected will throw an exception
    if (!this.client.connected) {
      console.log("Broker disconnected, can't send message.");
      return false;
    }
    if (message.length > 0) {
      const payLoad = {'topic': message};
      // You can additionally pass headers
      this.client.publish({destination: PUBLISH_QUEUE, body: JSON.stringify(payLoad),  headers: {_type: TYPE}, skipContentLengthHeader: true});
    }
    return true;
  }

  onSubmit = (text) => {
    // this.publishMessage(text)
    this.subscribeFromQueue(`${SUBSCRIBER_QUEUE}/${text.replace(" ","_")}`);
    this.sendTweetInterval = setInterval(() => { this.publishMessage(text) }, 5000);
  };

  onStop = () => {
    console.log("Stopped!!!");
    this.client.unsubscribe();
    this.initialQueueSetup();
    clearTimeout(this.sendTweetInterval)
  };

  render() {
    const { tweets } = this.props;

    return (
      <React.Fragment>
        <Container fluid>
          <Row>
            <Col xs={4}>
              { this.client &&
              <TweetInput client={this.client} onSubmit={this.onSubmit} onStop={this.onStop} />
              }
            </Col>
            <Col xs={8}>
              <Timeline>
                {tweets.map((tweet) => (
                  <Tweet {...tweet} key={tweet.id} />
                ))}
              </Timeline>
            </Col>
          </Row>
        </Container>
      </React.Fragment>
    );
  }
}

const mapStateToProps = (state) => ({
  activeUser: getUserById(state.users, state.users.active),
  tweets: getAllTweets(state.tweets)
    .map((tweet) => ({
      ...tweet,
      user: getUserById(state.users, tweet.userId),
    }))
    .sort(sortByDatetime),
});

const mapDispatchToProps = { logout, createTweet };

export default connect(mapStateToProps, mapDispatchToProps)(Home);
