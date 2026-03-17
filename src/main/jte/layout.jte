@import gg.jte.Content

@param String title
@param Content content

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title} — SBMDB</title>
    <style>
        :root {
            --bg:          #0e0e0e;
            --surface:     #1a1a1a;
            --border:      #2e2e2e;
            --accent:      #c9a84c;
            --accent-dim:  #7a6230;
            --text:        #e8e3d8;
            --text-muted:  #7a7670;
            --danger:      #c0392b;
            --font-heading: Georgia, 'Palatino Linotype', serif;
            --font-body:    system-ui, 'Segoe UI', sans-serif;
        }

        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            background: var(--bg);
            color: var(--text);
            font-family: var(--font-body), sans-serif;
            font-weight: 300;
            min-height: 100vh;
        }

        a { color: var(--accent); text-decoration: none; transition: opacity 0.2s; }
        a:hover { opacity: 0.75; }

        /* NAV */
        nav {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 1.25rem 2.5rem;
            border-bottom: 1px solid var(--border);
            position: sticky;
            top: 0;
            background: rgba(14,14,14,0.95);
            backdrop-filter: blur(8px);
            z-index: 100;
        }

        .nav-logo {
            font-family: var(--font-heading), serif;
            font-size: 1.5rem;
            color: var(--accent);
            letter-spacing: 0.05em;
        }

        .nav-links { display: flex; gap: 2rem; align-items: center; }

        .nav-links a {
            color: var(--text-muted);
            font-size: 0.875rem;
            letter-spacing: 0.08em;
            text-transform: uppercase;
            font-weight: 500;
        }

        .nav-links a:hover { color: var(--text); opacity: 1; }

        /* BUTTONS */
        .btn {
            display: inline-block;
            padding: 0.5rem 1.25rem;
            border-radius: 2px;
            font-size: 0.8rem;
            letter-spacing: 0.1em;
            text-transform: uppercase;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.2s;
            border: none;
            font-family: var(--font-body), sans-serif;
        }

        .btn-primary { background: var(--accent); color: #0e0e0e; }
        .btn-primary:hover { background: #e0bc60; opacity: 1; color: #0e0e0e; }

        .btn-ghost {
            background: transparent;
            color: var(--text-muted);
            border: 1px solid var(--border);
        }
        .btn-ghost:hover { border-color: var(--text-muted); color: var(--text); opacity: 1; }

        .btn-danger {
            background: transparent;
            color: var(--danger);
            border: 1px solid var(--danger);
        }
        .btn-danger:hover { background: var(--danger); color: white; opacity: 1; }

        /* LAYOUT */
        main { max-width: 1200px; margin: 0 auto; padding: 3rem 2.5rem; }

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: baseline;
            margin-bottom: 2.5rem;
            padding-bottom: 1.5rem;
            border-bottom: 1px solid var(--border);
        }

        .page-title {
            font-family: var(--font-heading), serif;
            font-size: 2.25rem;
            font-weight: 400;
            color: var(--text);
        }

        /* CARDS */
        .card {
            background: var(--surface);
            border: 1px solid var(--border);
            border-radius: 3px;
            padding: 1.5rem;
            transition: border-color 0.2s;
        }

        .card:hover { border-color: var(--accent-dim); }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 1.25rem; }

        /* FORMS */
        .form-group { display: flex; flex-direction: column; gap: 0.5rem; margin-bottom: 1.25rem; }

        label {
            font-size: 0.75rem;
            text-transform: uppercase;
            letter-spacing: 0.1em;
            color: var(--text-muted);
            font-weight: 500;
        }

        input, textarea, select {
            background: var(--bg);
            border: 1px solid var(--border);
            color: var(--text);
            padding: 0.65rem 0.875rem;
            border-radius: 2px;
            font-family: var(--font-body), sans-serif;
            font-size: 0.9rem;
            font-weight: 300;
            outline: none;
            transition: border-color 0.2s;
            width: 100%;
        }

        input:focus, textarea:focus { border-color: var(--accent); }
        textarea { resize: vertical; min-height: 100px; }

        /* PAGINATION */
        .pagination {
            display: flex;
            gap: 0.5rem;
            align-items: center;
            justify-content: center;
            margin-top: 3rem;
        }

        .pagination a, .pagination span {
            padding: 0.4rem 0.85rem;
            border: 1px solid var(--border);
            border-radius: 2px;
            font-size: 0.8rem;
            color: var(--text-muted);
        }

        .pagination a:hover { border-color: var(--accent); color: var(--accent); opacity: 1; }
        .pagination .active { border-color: var(--accent); color: var(--accent); }

        /* BADGE */
        .badge {
            display: inline-block;
            padding: 0.2rem 0.6rem;
            background: var(--accent-dim);
            color: var(--accent);
            border-radius: 2px;
            font-size: 0.75rem;
            letter-spacing: 0.05em;
        }

        /* STARS */
        .stars { color: var(--accent); letter-spacing: 0.1em; }

        /* FILTER BAR */
        .filter-bar {
            display: flex;
            flex-wrap: wrap;
            gap: 0.75rem;
            margin-bottom: 2rem;
            padding: 1.25rem;
            background: var(--surface);
            border: 1px solid var(--border);
            border-radius: 3px;
        }

        .filter-bar input { width: auto; flex: 1; min-width: 150px; }

        /* DIVIDER */
        .divider { border: none; border-top: 1px solid var(--border); margin: 2rem 0; }

        /* EMPTY STATE */
        .empty { text-align: center; padding: 5rem 2rem; color: var(--text-muted); }
        .empty-title {
            font-family: var(--font-heading), serif;
            font-size: 1.5rem;
            margin-bottom: 0.75rem;
            color: var(--text);
        }
    </style>
</head>
<body>
<nav>
    <a href="/" class="nav-logo">SBMDB</a>
    <div class="nav-links">
        <a href="/">Movies</a>
        <a href="/reviews">Reviews</a>
        <a href="/movies/create" class="btn btn-primary">+ Add Movie</a>
    </div>
</nav>
<main>
    ${content}
</main>
</body>
</html>