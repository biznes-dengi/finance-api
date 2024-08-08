import {Link} from 'react-router-dom';
import {APP_PATH, APP_TEXT} from '@shared/config';

export function NotFoundPage() {
	return (
		<div className='bg-red-300'>
			<h1>{APP_TEXT.pageNotFound}</h1>
			<div>
				<Link to={APP_PATH.root}>{APP_TEXT.goBackHome}</Link>
			</div>
		</div>
	);
}
