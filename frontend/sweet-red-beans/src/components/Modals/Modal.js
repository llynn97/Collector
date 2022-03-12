import React from 'react';
import '../../css/modal.css';

const Modal = (props) => {
  // 열기, 닫기, 모달 헤더 텍스트를 부모로부터 받아옴
  const { open, close, header } = props;

  return (
    // 모달이 열릴때 openModal 클래스가 생성된다.
    <div className={open ? 'openModal modal' : 'modal'}>
      {open ? (
        <section className='section'>
            <div className='buttonArea'>
              <button className="topClose" onClick={close}>
                {' '}
                &times;{' '}
              </button>
            </div>
            <div className='header'>
              {header}
            </div>
          <div className='main'>{props.children}</div>
          <footer>
            {/* <button className="close" onClick={close}>
              close
            </button> */}
          </footer>
        </section>
      ) : null}
    </div>
  );
};

export default Modal;