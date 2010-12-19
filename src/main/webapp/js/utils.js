function formSubmit()
{
	var form;
	for (var i = 0; i < arguments.length; i += 2) {
		if (i + 1 < arguments.length) {
			var elem = document.getElementById(arguments[i]);
			elem.value = arguments[i + 1];
			form = elem.form;
		}
	}
	form.submit();
}

function pfAction(act)
{
	formSubmit('pAction', act);
}

function pfConfirm(act)
{
	var ok = confirm("Are you sure?")
	if (ok)
		pfAction(act);
}

function pfDelete()
{
	pfConfirm('del');
}

function pfPrev()
{
	pfAction('prev');
}

function pfNext()
{
	pfAction('next');
}