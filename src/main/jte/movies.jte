@import org.example.sbmdb.entity.dto.MovieSummaryDTO
@import org.example.sbmdb.filter.MovieFilter
@import org.springframework.data.domain.Page
@import gg.jte.Content

@param Page<MovieSummaryDTO> movies
@param MovieFilter filter
@param String error = null
@param org.springframework.data.domain.Pageable pageable

@template.layout(title = "Movies", content = @`

    !{String query = "";}
    !{if (filter.title() != null && !filter.title().isBlank()) query += "&title=" + filter.title();}
    !{if (filter.director() != null && !filter.director().isBlank()) query += "&director=" + filter.director();}
    !{if (filter.description() != null && !filter.description().isBlank()) query += "&description=" + filter.description();}
    !{if (filter.runningTimeMin() != null) query += "&runningTimeMin=" + filter.runningTimeMin();}
    !{if (filter.runningTimeMax() != null) query += "&runningTimeMax=" + filter.runningTimeMax();}
    !{if (filter.releaseYearFrom() != null) query += "&releaseYearFrom=" + filter.releaseYearFrom();}
    !{if (filter.releaseYearTo() != null) query += "&releaseYearTo=" + filter.releaseYearTo();}
    !{if (filter.ratingMin() != null) query += "&ratingMin=" + filter.ratingMin();}
    !{if (filter.ratingMax() != null) query += "&ratingMax=" + filter.ratingMax();}
    !{String sortQuery = pageable.getSort().isSorted() ? "&sort=" + pageable.getSort().toString().replace(": ", ",") : "";}

    <div class="page-header">
        <h1 class="page-title">Movies</h1>
        <span style="color: var(--text-muted); font-size: 0.85rem;">${movies.getTotalElements()} titles</span>
    </div>

    @if(error != null)
        <div style="
            background: rgba(192, 57, 43, 0.1);
            border: 1px solid var(--danger);
            color: var(--danger);
            padding: 0.75rem 1.25rem;
            border-radius: 3px;
            margin-bottom: 1.25rem;
            font-size: 0.875rem;">
            ${error}
        </div>
    @endif

    <form class="filter-bar" method="get" action="/">
        <label>
            <input type="text" name="title" placeholder="Title..." value="${filter.title() != null ? filter.title() : ""}"/>
        </label>
        <label>
            <input type="text" name="director" placeholder="Director..." value="${filter.director() != null ? filter.director() : ""}"/>
        </label>
        <label>
            <input type="number" name="releaseYearFrom" placeholder="Year from..." style="max-width: 130px;"
                   value="${filter.releaseYearFrom() != null ? String.valueOf(filter.releaseYearFrom()) : ""}"/>
        </label>
        <label>
            <input type="number" name="releaseYearTo" placeholder="Year to..." style="max-width: 130px;"
                   value="${filter.releaseYearTo() != null ? String.valueOf(filter.releaseYearTo()) : ""}"/>
        </label>
        <label>
            <input type="number" name="ratingMin" placeholder="Min rating..." step="0.1" min="0" max="5" style="max-width: 120px;"
                   value="${filter.ratingMin() != null ? String.valueOf(filter.ratingMin()) : ""}"/>
        </label>
        <label>
            <input type="number" name="ratingMax" placeholder="Max rating..." step="0.1" min="0" max="5" style="max-width: 120px;"
                   value="${filter.ratingMax() != null ? String.valueOf(filter.ratingMax()) : ""}"/>
        </label>

        <button type="submit" class="btn btn-primary">Search</button>
        <a href="/" class="btn btn-ghost">Clear</a>
        <select name="sort" onchange="this.form.submit()" style="width: 150px;">
            <option value="title,asc">Title A-Z</option>
            <option value="title,desc">Title Z-A</option>
            <option value="releaseYear,desc">Newest First</option>
            <option value="releaseYear,asc">Oldest First</option>
            <option value="rating,desc">Highest Rated</option>
            <option value="rating,asc">Lowest Rated</option>
            <option value="runningTime,asc">Shortest First</option>
            <option value="runningTime,desc">Longest First</option>
        </select>
    </form>

    @if(movies.isEmpty())
        <div class="empty">
            <div class="empty-title">No movies found</div>
            <p>Try adjusting your search filters.</p>
        </div>
    @else
        <div class="grid">
            @for(MovieSummaryDTO movie : movies.getContent())
                <a href="/movies/${movie.id()}" style="text-decoration: none;">
                    <div class="card">
                        <div style="font-family: var(--font-heading), serif; font-size: 1.05rem; color: var(--text); margin-bottom: 0.3rem;">
                            ${movie.title()}
                        </div>
                        <div style="font-size: 0.78rem; color: var(--text-muted); margin-bottom: 0.75rem;">
                            ${String.join(", ", movie.directors())} · ${movie.releaseYear().getYear()}
                        </div>
                        <div style="display: flex; justify-content: space-between; align-items: center; padding-top: 0.75rem; border-top: 1px solid var(--border);">
                            @if(movie.rating() != null)
                                <div style="display: flex; align-items: center; gap: 0.5rem;">
                                    <div style="position: relative; display: inline-block; font-size: 0.9rem; letter-spacing: 0.1em;">
                                        <span style="color: var(--border);">★★★★★</span>
                                        <span style="position: absolute; left: 0; top: 0; overflow: hidden; width: ${String.format(java.util.Locale.US, "%.1f", movie.rating() / 5.0 * 100)}%;">
                                            <span style="color: var(--accent);">★★★★★</span>
                                        </span>
                                    </div>
                                    <span style="font-size: 0.8rem; color: var(--text-muted);">${String.format(java.util.Locale.US, "%.1f", movie.rating())}</span>
                                </div>
                            @else
                                <span style="font-size: 0.75rem; color: var(--text-muted);">No ratings yet</span>
                            @endif
                        </div>
                    </div>
                </a>
            @endfor
        </div>

        <div class="pagination">
            @if(!movies.isFirst())
                <a href="?page=${movies.getNumber() - 1}&size=${movies.getSize()}${query}${sortQuery}">←</a>
            @endif

            @for(int i = 0; i < movies.getTotalPages(); i++)
                @if(i == movies.getNumber())
                    <span class="active">${i + 1}</span>
                @else
                    <a href="?page=${i}&size=${movies.getSize()}${query}${sortQuery}">${i + 1}</a>
                @endif
            @endfor

            @if(!movies.isLast())
                <a href="?page=${movies.getNumber() + 1}&size=${movies.getSize()}${query}${sortQuery}">→</a>
            @endif
        </div>
    @endif
`)