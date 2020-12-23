import React from "react";
import PropTypes from "prop-types";
import moment from "moment";
import { Link } from "react-router-dom";
import {
  Card,
  CardHeader,
  CardContent,
  Avatar,
  Typography,
  withStyles,
} from "@material-ui/core";
import Markdown from "react-markdown";
import colorFrom from "../../utils/colors";
import { Base64 } from "js-base64";


const styles = (theme) => ({
  card: {
    marginBottom: theme.spacing.unit * 2,
  },
  cardMedia: {
    height: 0,
    paddingTop: "56.25%", // 16:9
  },
  content: {
    wordWrap: "break-word",
  },
  link: {
    textDecoration: "none",
    color: theme.palette.primary.dark,
  },
});

const Tweet = ({
  classes,
  id,
  text,
  createdAt,
  identifiedObject,
  user: { username },
  highlighted,
}) => {
  const image = Base64.isValid(text) && text.length > 200;

  return (
    <Card
      key={id}
      component={highlighted ? "div" : "li"}
      className={classes.card}
      elevation={highlighted ? 8 : 1}
    >
      <CardHeader
        avatar={
          <Avatar
            style={{
              backgroundColor: colorFrom(username),
            }}
          >
            {username[0]}
          </Avatar>
        }
        title={username}
        subheader={
          <Link to={`/tweet/${id}`} className={classes.link}>
            {moment(createdAt).fromNow()}
          </Link>
        }
      />

      {image && (
        <div style={{ textAlign: "center" }}>
          <img
            style={{ width: "500px", height: "500px" }}
            src={`data:image/jpeg;base64,${text}`}
          />
        </div>
      )}

      <CardContent className={classes.content}>
        <Typography variant={highlighted ? "display1" : "subheading"}>
          <Markdown
            source={identifiedObject}
            allowedTypes={[
              "root",
              "paragraph",
              "break",
              "emphasis",
              "strong",
              "delete",
              "link",
              "linkReference",
              "inlineCode",
              "code",
            ]}
          />
        </Typography>
      </CardContent>
    </Card>
  );
};

Tweet.propTypes = {
  classes: PropTypes.object.isRequired,
  id: PropTypes.string.isRequired,
  text: PropTypes.string.isRequired,
  createdAt: PropTypes.string.isRequired,
  user: PropTypes.shape({
    id: PropTypes.string,
    username: PropTypes.string.isRequired,
  }).isRequired,
  replyToId: PropTypes.string,
  repliedTweet: PropTypes.shape({
    text: PropTypes.string.isRequired,
  }),
  highlighted: PropTypes.bool,
};

Tweet.defaultProps = {
  replyToId: null,
  repliedTweet: null,
  highlighted: false,
};

export default withStyles(styles)(Tweet);
