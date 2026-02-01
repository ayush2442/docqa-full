import { useRef } from 'react';

function MediaPlayer({ document }) {
  const mediaRef = useRef(null);

  if (!document || document.fileType === 'PDF') {
    return null;
  }

  const seekTo = (timeInSeconds) => {
    if (mediaRef.current) {
      mediaRef.current.currentTime = timeInSeconds;
      mediaRef.current.play();
    }
  };

  window.seekToTimestamp = seekTo;

  const isVideo = document.fileType === 'VIDEO';
  const MediaElement = isVideo ? 'video' : 'audio';

  return (
    <div style={{ padding: '20px', border: '1px solid #ccc', marginBottom: '20px' }}>
      <h3>Media Player</h3>
      <MediaElement
        ref={mediaRef}
        controls
        style={{ width: '100%', maxWidth: isVideo ? '640px' : '100%' }}
      >
        <source src={`/api/documents/${document.id}/file`} />
        Your browser does not support the {isVideo ? 'video' : 'audio'} element.
      </MediaElement>
    </div>
  );
}

export default MediaPlayer;