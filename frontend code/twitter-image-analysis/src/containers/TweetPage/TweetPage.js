import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import { getUserById } from '../../modules/users';
import {
  createTweet,
  getTweetById
} from '../../modules/tweets';
import { getTweetMeta } from '../../modules';
import Tweet from '../../components/Tweet';
import Container from 'react-bootstrap/Container';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronLeft } from '@fortawesome/free-solid-svg-icons'

export class TweetPage extends React.Component {
  static propTypes = {
    match: PropTypes.shape({
      params: PropTypes.shape({
        tweetId: PropTypes.string.isRequired,
      }).isRequired,
    }).isRequired,
    activeUser: PropTypes.shape({
      id: PropTypes.string.isRequired,
      username: PropTypes.string.isRequired,
    }),
    tweet: PropTypes.object.isRequired,
    replies: PropTypes.arrayOf(PropTypes.object.isRequired).isRequired,
    createTweet: PropTypes.func.isRequired,
  };

  onSubmit = text => {
    const {
      match: { params },
      activeUser,
      createTweet,
    } = this.props;

    createTweet({
      userId: activeUser.id,
      text,
    });
  };

  render() {
    const { tweet } = this.props;

    if (!tweet) {
      return <Redirect to="/404" />;
    }

    return (
      <React.Fragment>
        <Container fluid >
        <FontAwesomeIcon icon={faChevronLeft} size="3x" onClick={this.props.history.goBack} style={{cursor:"pointer"}} />
          <Tweet {...tweet} highlighted />
        </Container>
      </React.Fragment>
    );
  }
}

const mapStateToProps = (state, { match: { params } }) => ({
  activeUser: getUserById(state.users, state.users.active),
  tweet: getTweetMeta(state, getTweetById(state.tweets, params.tweetId)),
});

const mapDispatchToProps = { createTweet };

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(TweetPage);
