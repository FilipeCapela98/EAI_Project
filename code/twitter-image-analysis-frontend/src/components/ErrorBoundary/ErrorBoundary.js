import React from 'react';
import { Typography, Grid } from '@material-ui/core';

class ErrorBoundary extends React.Component {
  state = {
    error: null,
    info: null,
  };

  componentDidCatch(error, info) {
    this.setState({ error, info });
  }

  render() {
    const { error, info } = this.state;

    if (!error) {
      return this.props.children;
    }

    return (
      <Grid container justify="center">
        <Grid item xs={11} sm={6} md={4}>
          <Typography variant="display3">Woops :(</Typography>
          <Typography paragraph variant="subheading">
            Looks like we have run into troubles
          </Typography>
          <pre
            style={{
              whiteSpace: 'pre-wrap',
              overflow: 'scroll',
              height: 300,
            }}
          >
            {info.componentStack}
          </pre>
        </Grid>
      </Grid>
    );
  }
}

export default ErrorBoundary;
