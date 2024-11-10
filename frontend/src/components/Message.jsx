import React from 'react'

const Message = ({ message, bgColor }) => {
  const styles = {
    padding: '1rem',
    marginBottom: '1rem',
    textAlign: 'center',
    color: 'white',
    fontWeight: 'bold',
    backgroundColor: bgColor
  }

  return (
    <>
      <div style={styles}>
        <p dangerouslySetInnerHTML={{ __html: message }} />
      </div>
    </>
  )
}

export default Message
