import ReactDOM from 'react-dom/client';
import {AppProvider} from '@app/providers';
import './index.css';

ReactDOM.createRoot(document.getElementById('root')!).render(<AppProvider />);

console.log('1');
