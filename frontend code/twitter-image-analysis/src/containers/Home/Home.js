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
// import SockJsClient from 'react-stomp';
import { Client, Message  } from '@stomp/stompjs';

export class Home extends React.Component {
  static propTypes = {
    activeUser: PropTypes.shape({
      id: PropTypes.string.isRequired,
      username: PropTypes.string,
    }).isRequired,
    tweets: PropTypes.arrayOf(PropTypes.object.isRequired).isRequired,
    createTweet: PropTypes.func.isRequired,
  };
  
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    console.log('Component did mount');
    this.client = new Client();

    this.client.configure({
      brokerURL: 'ws://localhost:61616',
      connectHeaders: {
        login: 'admin',
        passcode: 'admin',
      },
      onConnect: () => {
        console.log('onConnect');

        // this.client.subscribe('/queue/now', message => {
        //   console.log(message);
        //   this.setState({serverTime: message.body});
        // });

        this.client.subscribe('/topic/topic_name', message => {
          alert(message.body);
        });
      },
      // Helps during debugging, remove in production
      debug: (str) => {
        console.log(new Date(), str);
      }
    });

    this.client.activate();
  }
  
  publishMessage(message) {
    // trying to publish a message when the broker is not connected will throw an exception
    if (!this.client.connected) {
      console.log("Broker disconnected, can't send message.");
      return false;
    }
    if (message.length > 0) {
      const payLoad = {topic: message };
      // You can additionally pass headers
      this.client.publish({destination: '/streaming-service-heartbeats', body: JSON.stringify(payLoad)});
    }
    return true;
  }

  onSubmit = (text) => {
    this.publishMessage(text)
    const {
      createTweet,
      activeUser: { id: userId },
    } = this.props;
    createTweet({ userId, text });
  };

  render() {
    const { tweets } = this.props;

    return (
      <React.Fragment>
        <Container fluid>
          <Row>
            <Col xs={3}>
            {/* <TweetInput onSubmit={this.onSubmit} /> */}
              { this.client &&
              <TweetInput client={this.client} onSubmit={this.onSubmit} />
              }
            </Col>
            <Col>
              <Timeline>
                {tweets.map((tweet) => (
                  <Tweet {...tweet} key={tweet.id} />
                ))}
                {/* <SockJsClient
                  url="http://localhost:61614/ws"
                  topics={["/topics/all"]}
                  debug= {true}
                  onMessage={(msg) => {
                    console.log(msg);
                  }}
                  ref={(client) => {
                    this.clientRef = client;
                  }}
                /> */}
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
      repliedTweet: getTweetById(state.tweets, tweet.replyToId),
      user: getUserById(state.users, tweet.userId),
    }))
    .sort(sortByDatetime),
});

const mapDispatchToProps = { logout, createTweet };

export default connect(mapStateToProps, mapDispatchToProps)(Home);
