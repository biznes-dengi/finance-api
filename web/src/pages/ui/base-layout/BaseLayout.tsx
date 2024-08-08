import {Outlet} from 'react-router-dom';

export function BaseLayout() {
	return (
		<div className='flex min-h-screen flex-col'>
			<Outlet />
		</div>
	);
}
