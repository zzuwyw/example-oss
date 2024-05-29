import React from 'react';
import {
  CopyrightOutlined
} from "@ant-design/icons";

const Footer: React.FC = () => {
  return (
    <>
      <div style={{
        marginBlock: 0,
        marginBlockStart: 48,
        marginBlockEnd: 24,
        marginInline: 0,
        paddingBlock: 0,
        paddingInline: 16,
        textAlign: 'center',
        color: 'rgba(0, 0, 0, 0.88)',
        fontSize: 14,
        fontStyle: "normal",
        unicodeBidi: "isolate"
      }}>
        <span style={{
          alignItems: "center",
          color: "inherit",
          lineHeight: 0,
          textAlign: "center",
          textTransform: "none",
          textRendering: "optimizeLegibility",
        }}><CopyrightOutlined /></span>
        <span> Powered by Alex Lab</span>
      </div>
    </>
  );
};

export default Footer;
