/* eslint-disable react/prop-types */
const Message = ({ msg, bgColor }: any) => {
    const styles: any = {
      padding: '1rem',
      marginBottom: '1rem',
      textAlign: 'center',
      color: '#fff',
      fontWeight: 'bold',
      backgroundColor: bgColor
    };
    return (
      <div style={styles}>
        <p dangerouslySetInnerHTML={{ __html: msg }} />
      </div>
    );
  };
  
  export default Message;
  