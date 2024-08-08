import {Outlet} from 'react-router-dom';

export function AppLayout() {
	return (
		<div className='mx-auto max-w-xl'>
			<Outlet />
		</div>
	);
}
