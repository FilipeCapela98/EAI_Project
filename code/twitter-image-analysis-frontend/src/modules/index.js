import { combineReducers } from 'redux';
import { connectRouter } from 'connected-react-router';
import history from '../utils/history';

import users, * as fromUsers from './users';
import tweets from './tweets';

export const rootReducer = combineReducers({
  users,
  tweets,
});

export default connectRouter(history)(rootReducer);

export const getTweetMeta = (state, tweet) => {
  const user = fromUsers.getUserById(state.users, tweet.userId);
  return { ...tweet, user };
};
